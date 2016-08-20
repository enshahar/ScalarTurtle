package com.enshahar.turtle.unit

/**
  * Created by hyunsok on 2016-08-13.
  */
sealed abstract class AngleUnit {
  val toRadian: Double
  val toDegree: Double
  val toMinute: Double
  val toSecond: Double
  val name: String
  def toRadians(value: Double): Double = toRadian * value
  def toDegrees(value: Double): Double = toDegree * value
  def toMinutes(value: Double): Double = toMinute * value
  def toSeconds(value: Double): Double = toSecond * value

  def fromRadians(value: Double): Double = value / toRadian
  def fromDegrees(value: Double): Double = value / toDegree
  def fromMinutes(value: Double): Double = value / toMinute
  def fromSeconds(value: Double): Double = value / toSecond

  def convert(value: Double, unit: AngleUnit): Double =  AngleUnit.convert(value, this, unit)

  def normalize(value: Double): Double
}

object AngleUnit {
  def convert(value: Double, from: AngleUnit, to: AngleUnit): Double = to match {
    case RADIAN => from.toRadians(value)
    case DEGREE => from.toDegrees(value)
    case MINUTE => from.toMinutes(value)
    case SECOND => from.toSeconds(value)
  }

  def apply(name: String): AngleUnit = name match {
    case "radian" => RADIAN
    case "degree" => DEGREE
    case "minute" => MINUTE
    case "second" => SECOND
  }
}
case object RADIAN extends AngleUnit {
  override val name = "radian"
  override val toRadian = 1.0
  override val toDegree = 180.0 / Math.PI
  override val toMinute = 180.0 / Math.PI * 60.0
  override val toSecond = 180.0 / Math.PI * 60.0 * 60.0
  override def normalize(value: Double): Double = value % (2 * Math.PI)
}

case object DEGREE extends AngleUnit {
  override val name = "degree"
  override val toRadian = Math.PI / 180.0
  override val toDegree = 1.0
  override val toMinute = 60.0
  override val toSecond = 3600.0
  override def normalize(value: Double): Double = value % 360.0
}

case object MINUTE extends AngleUnit {
  override val name = "minute"
  override val toRadian = Math.PI / 180.0 / 60.0
  override val toDegree = 1.0 / 60.0
  override val toMinute = 1.0
  override val toSecond = 60.0
  override def normalize(value: Double): Double = value % (360.0 * 60.0)
}

case object SECOND extends AngleUnit {
  override val name = "second"
  override val toRadian = Math.PI / 180.0 / 60.0 / 60.0
  override val toDegree = 1.0 / 60.0 / 60.0
  override val toMinute = 1.0 / 60.0
  override val toSecond = 1.0
  override def normalize(value: Double): Double = value % (360.0 * 60.0 * 60.0)
}

