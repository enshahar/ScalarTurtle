package com.enshahar.turtle.oo

import scala.swing._
import scala.swing.event.ButtonClicked
import java.awt.image.BufferedImage

import scala.language.postfixOps

import com.enshahar.turtle.common
import com.enshahar.turtle.unit._
import scala.util.Random

/**
  * Created by hyunsok on 2016-08-14.
  */
object OOClient extends SimpleSwingApplication with common.Logger with common.Graphics {
  val HEIGHT =  480
  val WIDTH = 640

  val turtle = new Turtle(this, this)
  val img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR)
  val gr = img.createGraphics()
  gr.translate(WIDTH/2, HEIGHT/2) // mamke the center of image to (0,0)
  gr.scale(1,-1)  // reverse the y coordinate

  val startButton = new Button {
    text = "Start"
  }

  val canvas = new Panel {
    border = Swing.EmptyBorder(0,0,HEIGHT,WIDTH)

    override def paintComponent(g: swing.Graphics2D) = {
      g.drawImage(img, 0, 0, null)
    }
  }

  def top = new MainFrame {
    title = "OO Turtle"
    contents = new BoxPanel(Orientation.Vertical) {
      contents += startButton
      contents += canvas
    }
  }

  listenTo(startButton)

  reactions += {
    case ButtonClicked(b) =>
      if(b==startButton) {
        turtle.On()
        (1 to 360).foreach { _ =>
          (1 to 4).foreach { _ =>
            turtle.move(300)
            turtle.turn(90 deg)
          }
          turtle.setColor(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
          turtle.turn(1 deg)
        }
        canvas.repaint()
      }
  }

  // logger method
  override def apply(s:String): Unit = println(s)

  // graphics methods
  var prevPos: common.Position = (0.0, 0.0)
  override def lineTo(pos: common.Position) =  {
    gr.drawLine(prevPos._1.toInt, prevPos._2.toInt, pos._1.toInt, pos._2.toInt)
    prevPos = pos
  }
  override def moveTo(pos: common.Position) =  { prevPos = pos; }
  override def setColor(color: common.Color) = { gr.setColor(new Color(color._1, color._2, color._3)) }
  override def clear() = { canvas.repaint() }
}
