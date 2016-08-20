package com.enshahar.turtle.oo

import java.awt.image.BufferedImage

import com.enshahar.turtle.common
import com.enshahar.turtle.unit._

import scala.language.postfixOps
import scala.swing._
import scala.swing.event.ButtonClicked
import scala.util.Random

/**
  * Created by hyunsok on 2016-08-14.
  */
object OOClient extends SimpleSwingApplication with common.StdoutLogger with common.AWTGraphics {
  val HEIGHT =  480
  val WIDTH = 640

  val turtle = new Turtle(this, this)
  val img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR)
  val gr = img.createGraphics()
  gr.translate(WIDTH/2, HEIGHT/2) // make the center of image to (0,0)
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
        turtle.on()
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
}
