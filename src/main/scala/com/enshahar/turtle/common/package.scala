package com.enshahar.turtle


/**
  * Created by hyunsok on 2016-08-14.
  */
package object common {
  type Color = (Int, Int, Int)
  type Position = (Double, Double)

  import com.enshahar.turtle.unit._

  def calcPosition( d:  Double, a: Angle ): Position = (d * math.cos(a.toRadians),
    d * math.sin(a.toRadians))

  implicit final class PositionOps(x:Position) extends Position(x._1, x._2) {
    def +(p:Position):Position = (_1 + p._1, _2 + p._2)
    def -(p:Position):Position = (_1 - p._1, _2 - p._2)
  }
}
