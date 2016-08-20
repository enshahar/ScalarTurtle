package com.enshahar.turtle.unit

/**
  * Created by hyunsok on 2016-08-13.
  */
object Angle {
  def apply(value: Double, unit: AngleUnit) = new Angle(value, unit)
  def apply(value: Double, unit: String) = new Angle(value, AngleUnit(unit))
}

final class Angle(val v: Double, val unit: AngleUnit) {
  val value: Double = unit.normalize(v)
  def toRadians = unit.toRadians(value)
  def toDegrees = unit.toDegrees(value)
  def toMinutes = unit.toMinutes(value)
  def toSeconds = unit.toSeconds(value)
  def toUnit(u: AngleUnit) = unit.convert(value, u)

  def +(a: Angle): Angle = Angle(value+a.toUnit(this.unit), this.unit)

  private[this] val unitString = unit.name
  override def toString = f"$value%.2f $unitString"
}

trait AngleConversion extends Any {
  protected def angleIn(unit: AngleUnit): Angle

  def radians = angleIn(RADIAN)
  def radian = radians
  def rad = radians

  def degrees = angleIn(DEGREE)
  def degree = degrees
  def deg = degrees

  def minutes = angleIn(MINUTE)
  def minute = minutes
  def mins = minutes

  def seconds = angleIn(SECOND)
  def second = seconds
  def secs = seconds
}
