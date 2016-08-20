package com.enshahar.turtle.functional

import java.awt.image.BufferedImage

import com.enshahar.turtle.common
import com.enshahar.turtle.unit._

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.swing._
import scala.swing.event.ButtonClicked
import scala.util.Random

object FpClient extends SimpleSwingApplication with common.StdoutLogger with common.AWTGraphics {
  val WIDTH = 640
  val HEIGHT = 480
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

  val turtle = Turtle.initial

  implicit class ArrowClass(v: (TurtleState, String))  {
  }

  val log = this
  val move = Turtle.move { (s, st) => {
    log(s)
    if(st.penOn)
      lineTo(st.position)
    else
      moveTo(st.position)
  } }

  val on = Turtle.on { (s, st) => log(s) }
  val off = Turtle.off { (s, st) => log(s) }
  val setColor2 = ( r: Int, g: Int, b: Int ) => Turtle.setColor { (s, st) => log(s); setColor(r,g,b) } (r,g,b)
  val turn = Turtle.turn { (s, st) => log(s) }
  val flush = (s: TurtleState) => { canvas.repaint(); s }

  implicit class FunctionComposer(st: TurtleState) {
    def |>(f: TurtleState => TurtleState) = f(st)
  }

  val rectangle = (init: TurtleState) => {
    val moveAndTurn = (s: TurtleState) => s |> move(300) |> turn(90 deg)
    init |> moveAndTurn |> moveAndTurn |> moveAndTurn  |> moveAndTurn
  }

  val changeColorToRandom = (s: TurtleState) => {
    s |> setColor2(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
  }

  val changeColorAndDrawRect = (s: TurtleState) => s |> changeColorToRandom |> rectangle

  @tailrec
  def repeat(n: Int)(f: TurtleState => TurtleState)(s: TurtleState): TurtleState =
    if(n==0) s else repeat(n-1)(f)(f(s))

  reactions += {
    case ButtonClicked(b) =>
      if(b==startButton) {
        //turtle |> on |> setColor2(255,0,0) |> rectangle |> flush
        turtle |> on |> repeat(360){ (s:TurtleState) => s |> changeColorAndDrawRect |> turn(1 deg) } |> flush
      }
  }
}
