package client

import akka.actor.Actor
import akka.io.Tcp.Connected
import akka.util.ByteString
import org.json4s.Formats
import org.json4s.jackson.JsonMethods._

/**
 * Created by d3ft0uch.
 */
class GameActor extends Actor {
  implicit def formats: Formats = org.json4s.DefaultFormats

  override def receive: Receive = {
    case data: ByteString =>
      val str = data.utf8String
      val response = parse(str).extract[Map[String, Any]]
      val result = Result(response("fin").toString.toBoolean, response("prize").asInstanceOf[List[Int]])
      println(s"You won ${result.prize.mkString("+")}!")
      if (!result.fin) {
        executeStep(Messages.continue)
      }
      else {
        Messages.goodbye
        Boot.system.terminate()
      }

    case Connected(remote, local) => executeStep(Messages.welcome)
    case _ =>
  }

  def executeStep(msg: => Unit): Unit = {
    msg
    scala.io.StdIn.readLine().toLowerCase match {
      case "y" => Boot.client ! ByteString("continue")
      case "n" =>
        Boot.client ! ByteString("stop")
        Messages.goodbye
        Boot.system.terminate()
      case _ => executeStep(msg)
    }
  }

  case class Result(fin: Boolean, prize: List[Int])

}

object Messages {

  def welcome = println("Start a game? Y/N")

  def goodbye = println("Goodbye!")

  def continue = println("Wanna take another try? Y/N")
}

