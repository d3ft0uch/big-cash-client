package client

import java.net.InetSocketAddress

import akka.actor.{Props, ActorSystem}


/**
 * Created by d3ft0uch.
 */
object Boot extends App {
  implicit val system = ActorSystem()
  val game = system.actorOf(Props(classOf[GameActor]))
  val client = system.actorOf(Client.props(new InetSocketAddress(ServiceConfig.getServerHost, ServiceConfig.getServerPort), game))
}

