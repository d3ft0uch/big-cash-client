package client

import java.io.FileInputStream
import java.util.Properties

/**
 * Created by d3ft0uch.
 */
object ServiceConfig {

  private val p = new Properties

  p.load(new FileInputStream("service.properties"))

  def getServerHost = getStringParam("server_host")

  def getServerPort = getIntParam("server_port")

  private def getStringParam(paramName: String, defaultValue: String = ""): String = {
    val paramValue = p.getProperty(paramName)
    if (paramValue == null) defaultValue else paramValue
  }

  private def getIntParam(paramName: String, defaultValue: Int = 0): Int = {
    val paramValue = p.getProperty(paramName)
    if (paramValue == null) defaultValue else paramValue.toInt
  }

}