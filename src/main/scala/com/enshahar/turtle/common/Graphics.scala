package com.enshahar.turtle.common

import java.awt.Graphics2D

import scala.swing.Panel

/**
  * Created by hyunsok on 2016-08-14.
  */
trait Graphics {
  /* The position is general Cartesian coordinate and have no limit
     Implementor can crop the drawing action or just rescale the canvas as freely
   */
  def lineTo(pos: Position)
  def moveTo(pos: Position)
  def setColor(color: Color)
  def clear()
}

trait AWTGraphics extends Graphics {
  val gr: Graphics2D
  val canvas: Panel
  val WIDTH : Int
  val HEIGHT: Int

  // graphics methods
  var prevPos: Position = (0.0, 0.0)
  override def lineTo(pos: Position) =  {
    gr.drawLine(prevPos._1.toInt, prevPos._2.toInt, pos._1.toInt, pos._2.toInt)
    prevPos = pos
  }
  override def moveTo(pos: Position) =  { prevPos = pos; }
  override def setColor(color: Color) = { gr.setColor(new java.awt.Color(color._1, color._2, color._3)) }
  override def clear() = {
    gr.setColor( java.awt.Color.BLACK )
    gr.drawRect( -WIDTH / 2,-HEIGHT / 2, WIDTH, HEIGHT )
    canvas.repaint()
  }
}