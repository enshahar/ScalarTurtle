package com.enshahar.turtle.unit

/**
  * Created by hyunsok on 2016-08-15.
  */
object DoubleOps {
  // implicit class for comppare double numbers
  implicit class DoubleComparisionOps(d: Double) {
    def <==>(other: Double, precision: Double = 1e-5) = {
      val rv = Math.abs(other - d) <= precision
      println(s"$d <==> $other = $rv")
      rv
    }
  }
}
