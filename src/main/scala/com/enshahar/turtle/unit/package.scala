package com.enshahar.turtle

import scala.language.implicitConversions

/**
  * Created by hyunsok on 2016-08-13.
  */
package object unit {
  implicit final class AngleInt(private val n: Int) extends AnyVal with AngleConversion {
    override protected def angleIn(unit: AngleUnit): Angle = Angle(n.toDouble, unit)
  }
  implicit final class AngleLong(private val n: Long) extends AnyVal with AngleConversion {
    override protected def angleIn(unit: AngleUnit): Angle = Angle(n.toDouble, unit)
  }
  implicit final class AngleDouble(private val n: Double) extends AnyVal with AngleConversion {
    override protected def angleIn(unit: AngleUnit): Angle = Angle(n, unit)
  }
}
