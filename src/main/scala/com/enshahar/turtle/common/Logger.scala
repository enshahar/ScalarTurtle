/**
  * Created by hyunsok on 2016-08-12.
  */
package com.enshahar.turtle.common

trait Logger {
  def apply(log: String):Unit
}

trait StdoutLogger extends Logger {
  // logger method
  override def apply(s:String): Unit = println(s)
}