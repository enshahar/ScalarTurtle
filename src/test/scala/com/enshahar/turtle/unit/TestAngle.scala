package com.enshahar.turtle.unit

import org.scalatest._

import DoubleOps._

import scala.language.postfixOps

/**
  * Created by hyunsok on 2016-08-13.
  */
class TestAngle extends FlatSpec {
  "radians" should "converted back to itself" in {
    assert((1 radians).value <==> (1 rad).toRadians )
    assert((0 rad).value <==> (0 rad).toRadians )
    assert((-3.14 radian).value <==> (-3.14 rad).toRadians )
  }

  it should "converted to degrees properly" in {
    assert((Math.PI radian).toDegrees <==> 180.0)
    assert((2*Math.PI radian).toDegrees <==> 360.0)
    assert((Math.PI/2.0 radian).toDegrees <==> 90.0)
  }

  it should "converted to others and back to itself" in {
    assert(((1 radians).toDegrees deg).toRadians <==> 1.0)
    assert(((Math.PI radians).toDegrees deg).toRadians <==> Math.PI)
    assert(((1 radians).toMinutes mins).toRadians <==> 1.0)
    assert(((Math.PI radians).toMinutes mins).toRadians <==> Math.PI)
    assert(((1 radians).toSeconds secs).toRadians <==> 1.0)
    assert(((Math.PI radians).toSeconds secs).toRadians <==> Math.PI)
  }

  "degrees" should "converted back to itself" in {
    assert((1 degrees).value <==> (1 degree).toDegrees)
    assert((0 degree).value <==> (0 deg).toDegrees)
    assert((-3.14 deg).value <==> (-3.14 deg).toDegrees)
  }

  it should "converted to others and back to itself" in {
    assert(((1 deg).toRadians rad).toDegrees <==> 1.0)
    assert(((Math.PI deg).toRadians rad).toDegrees <==> Math.PI)
    assert(((1 deg).toMinutes mins).toDegrees <==> 1.0)
    assert(((Math.PI deg).toMinutes mins).toDegrees <==> Math.PI)
    assert(((1 deg).toSeconds secs).toDegrees <==> 1.0)
    assert(((Math.PI deg).toSeconds secs).toDegrees <==> Math.PI)
  }

  "minutes" should "converted back to itself" in {
    assert((1 minutes).value <==> (1 mins).toMinutes)
    assert((0 minutes).value <==> (0 mins).toMinutes)
    assert((-3.14 mins).value <==> (-3.14 mins).toMinutes)
  }

  it should "converted to degree and back to itself" in {
    assert(((1 mins).toRadians rad).toMinutes <==> 1.0)
    assert(((Math.PI mins).toRadians rad).toMinutes <==> Math.PI)
    assert(((1 mins).toDegrees deg).toMinutes <==> 1.0)
    assert(((Math.PI mins).toDegrees deg).toMinutes <==> Math.PI)
    assert(((1 mins).toSeconds secs).toMinutes <==> 1.0)
    assert(((Math.PI mins).toSeconds secs).toMinutes <==> Math.PI)
  }

  "seconds" should "converted back to itself" in {
    assert((1 seconds).value <==> (1 secs).toSeconds)
    assert((0 second).value <==> (0 secs).toSeconds)
    assert((-3.14 secs).value <==> (-3.14 secs).toSeconds)
  }

  it should "converted to degree and back to itself" in {
    assert(((1 secs).toRadians rad).toSeconds <==> 1.0)
    assert(((Math.PI secs).toRadians rad).toSeconds <==> Math.PI)
    assert(((1 secs).toDegrees deg).toSeconds <==> 1.0)
    assert(((Math.PI secs).toDegrees deg).toSeconds <==> Math.PI)
    assert(((1 secs).toMinutes mins).toSeconds <==> 1.0)
    assert(((Math.PI secs).toMinutes mins).toSeconds <==> Math.PI)
  }
}
