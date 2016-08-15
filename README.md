#  ScalarTurtle

스칼라를  사용한  터틀  그래픽  

이  프로젝트는  [거북이를  살펴보는  13가지  방법](https://fsharpforfunandprofit.com/posts/13-ways-of-looking-at-a-turtle/)에서  힌트를  얻어서  시작한  것입니다.  물론  재미로.

##  oo  turtle

일단  객체지향  거북이를  만들었습니다.  거북이  자체는  로깅과  그래픽  출력을  위해  `Logger`와  `Graphics`를  외부에서  받습니다.  

```scala
class  Turtle(log:  Logger,  drawer:  Graphics)  {
```

`Logger`와  `Graphics`는  별로  대단한  것은  없는  트레이트입니다.

거북이  상태는  현재  위치,  현재  색,  펜이  켜져있는지  여부,  거북이  머리가  향하는  방향입니다.

```scala
    private  var  position:  Position  =  (0.0,  0.0)
    private  var  angle:  Angle  =  0.0  deg
    private  var  color:  Color  =  (0,  0,  0)
    private  var  penOn  =  false
```

맨  처음엔  `(0,0)`으로  위치시키고,  머리는  `0`(오른쪽  방향)  도(degree)로  설정합니다.  `0.0  deg`에서  `deg`는  각도를  지정하는  후위  연산자입니다.  이에  대해서는  `com.enshahar.turtle.unit`  패키지  디렉터리에  있는  `readme.md`에서  설명하겠습니다.

```scala
    drawer.moveTo(0,0)
```

스칼라의  클래스  정의시,  특별한  메서드에  속하지  않은  코드가  클래스  몸통(본문)에  있으면,  클래스의  생성자에서  해당  코드를  실행한다는  것은  알고  있으시리라  봅니다.  `drawer.moveTo()`를  호출해서  그래픽스  객체의  정중앙부터  그림을  그려  나가게  합니다.

```scala
    def  move(distance:  Double)  =  {
        val  diff  =  calcPosition(position,  distance,  angle)
        position  +=  diff
        if(penOn)  {
            drawer.lineTo(position)
        }  else  {
            drawer.moveTo(position)
        }
        log(f"Move  $distance%.1f  =  ($position,  $angle,  $color,  $penOn)")
    }
```

`move`는  거북이를  현재  머리  방향으로  `distance`만큼  이동시킵니다.  펜이  켜져  있다면(`penOn`)  `drawer`에  그림을  그리라는  명령을  내리고,  그렇지  않다면  `moveTo`로  단순이동만  합니다.

```scala
    def  turn(a:  Angle):  Unit  =  {
        angle  +=  a
        log(f"Turn  $a  =  ($position,  $angle,  $color,  $penOn)")
    }
```

`turn`은  거북이의  머리  방향을  바꿉니다.  현재  각도  대비  증가  또는  감소할  **상대적**  각도를  지정합니다.  단위는  일반적인  각도(예:  `1  deg`),  라디안(예:  `Math.PI  rad`),  분(예:  `55  mins`),  초(예:  `-35  secs`)를  지정할  수  있습니다.  현재로써는  `turn`해도  거북이의  내부  상태만  바뀌고,  화면상  변화는  없습니다.

```scala
    def  On():  Unit  =  penOn  =  true
    def  Off():  Unit  =  penOn  =  false
```

`On`과  `Off`는  펜을  켜고  끕니다.

```scala
    def  setColor(c:  Color)  =  {
        color  =  c
        drawer.setColor(c)
    }
```

`setColor`는  색을  지정합니다.  색은  `(Int,  Int,  Int)`  형태의  3-튜플로,  각각  적,  녹,  청  (빛의  3원색)  입니다.

```
    private  def  calcPosition(d:    Double,  a:  Angle):  Position  =  (d  *  math.cos(a.toRadians),
          d  *  math.sin(a.toRadians))
```

위치  계산을  위한  도우미  메서드입니다.  `d`만큼  `a`각도로  이동하기  위해  삼각함수를  사용해  `x`  방향과  `y`  방향으로의  정사영  벡터  값을  구해서  튜플로  돌려줍니다.  

```
    private  implicit  final  class  PositionOps(x:Position)  extends  Position(x._1,  x._2)  {
        def  +(p:Position):Position  =  (_1  +  p._1,  _2  +  p._2)
        def  -(p:Position):Position  =  (_1  -  p._1,  _2  -  p._2)
    }
}
```

스칼라는  두  튜플  사이의  덧셈을  지원하지  않기  때문에,  여기  간단하게  `+`  연산을  지원하는  암시적  클래스를  만들어서  연산이  가능하게  만들었습니다.  `move`함수  안에서  `position  +=  diff`라고  하면,  실제로는  `position  =  PositionOps(position).+(diff)`가  실행됩니다.

##  oo  turtle  클라이언트

간단하게  스윙  애플리케이션을  만들었습니다.

```scala
object  OOClient  extends  SimpleSwingApplication  with  common.Logger  with  common.Graphics  {
```

`SimpleSwingApplication`은  간단한  스윙  애플리케이션을  위한  추상  클래스이고,  `Logger`와  `Graphics`는  거북이  인스턴스  생성시  필요한  트레이트입니다.  `Graphics`가  `AWT`에도  있기  때문에,  임포트할  때  `common`까지만  임포트해서  구별을  했습니다.

```scala
    val  HEIGHT  =    480
    val  WIDTH  =  640
```

기본  캔버스  너비와  높이를  정합니다.

```scala
    val  turtle  =  new  Turtle(this,  this)
```
거북이를  하나  만듭니다.

```scala
    val  img  =  new  BufferedImage(WIDTH,  HEIGHT,  BufferedImage.TYPE_3BYTE_BGR)
    val  gr  =  img.createGraphics()
```

(화면에  직접  그리지  않고  메모리상에서  그림을  그리기  위한)  오프스크린  버퍼를  하나  정의하기  위해  자바  AWT의  `BufferedImage`를  사용합니다.  `Graphics2D`  객체를  생성합니다.  `Graphics`를  사용하지  않는  이유는  좌표  변환을  편하게  수행하기  위해서입니다.

```
    gr.translate(WIDTH/2,  HEIGHT/2)  //  mamke  the  center  of  image  to  (0,0)
    gr.scale(1,-1)    //  reverse  the  y  coordinate
```

`translate`는  원점을  이동해  주며,  `scale`는  `x`와  `y`  좌표에  곱해질  배율을  지정합니다.  여기서  `y`에  음수를  곱하면  좌표  방향을  뒤집는  효과가  있습니다.  데카르트  직교  좌표에서  `x`축은  *왼쪽->오른쪽*으로  커지고,  `y`축은  *아래쪽->위쪽*으로  커지지만,  일반적인  이미지  처리  좌표계에서는  `x`축이  커지는  방향은  데카르트  직교  좌표와  같지만,  `y`축이  *위쪽->아래쪽*으로  커지기  때문에,  `y`축의  부호를  뒤집어야  합니다.

```
    val  startButton  =  new  Button  {
        text  =  "Start"
    }

    val  canvas  =  new  Panel  {
        border  =  Swing.EmptyBorder(0,0,HEIGHT,WIDTH)
```

버튼과  캔버스  위젯을  정의합니다.  캔버스는  `Panel`을  상속한  익명  클래스로  정의합니다.

```scala
        override  def  paintComponent(g:  swing.Graphics2D)  =  {
            g.drawImage(img,  0,  0,  null)
        }
    }
```

캔버스가  색칠될  때마다  `paintComponent`가  호출됩니다.  여기서  `drawImage`를  사용해  오프스크린  이미지를  캔버스  화면으로  옮겨  줍니다.

```scala
    def  top  =  new  MainFrame  {
        title  =  "OO  Turtle"
        contents  =  new  BoxPanel(Orientation.Vertical)  {
            contents  +=  startButton
            contents  +=  canvas
        }
    }
```

`top`은  최상위  윈도우입니다.  그  안에  아까  만든  위젯을  `BoxPanel`을  사용해  위아래로  위치시킵니다.

```scala
    listenTo(startButton)
```

`startButton`에서  발생하는  이벤트를  구독합니다.

```
    reactions  +=  {
        case  ButtonClicked(b)  =>
            if(b==startButton)  {
                turtle.On()
                (1  to  360).foreach  {  _  =>
                    (1  to  4).foreach  {  _  =>
                        turtle.move(300)
                        turtle.turn(90  deg)
                    }
                    turtle.setColor(Random.nextInt(256),  Random.nextInt(256),  Random.nextInt(256))
                    turtle.turn(1  deg)
                }
                canvas.repaint()
            }
    }
```

이벤트  처리  루틴을  정의합니다.  `ButtonClicked`가  발생했다면  버튼이  `startButton`인지  확인하고,  간단하게  터틀에  명령을  내려서  그림을  그립니다.  `300`  크기의  정4각형을  `1`도씩  바꿔가면서  `360`개  그립니다.

```scala
    //  logger  method
    override  def  apply(s:String):  Unit  =  println(s)
```

`Logger`  트레이트에서  처리해야  하는  메소드입니다.  문자열을  콘솔에  출력합니다.

```
    //  graphics  methods
    var  prevPos:  common.Position  =  (0.0,  0.0)
    override  def  lineTo(pos:  common.Position)  =    {
        gr.drawLine(prevPos._1.toInt,  prevPos._2.toInt,  pos._1.toInt,  pos._2.toInt)
        prevPos  =  pos
    }
    override  def  moveTo(pos:  common.Position)  =    {  prevPos  =  pos;  }
    override  def  setColor(color:  common.Color)  =  {  gr.setColor(new  Color(color._1,  color._2,  color._3))  }
    override  def  clear()  =  {  
        gr.setColor(  Color.BLACK  )
        gr.drawRect(  -WIDTH  /  2,-HEIGHT  /  2,  WIDTH,  HEIGHT  )
        canvas.repaint()  
     }
}

```

`Graphics`  트레이트에서  처리해야  하는  메소드들입니다.  직전  위치를  항상  `prevPos`에  저장해  두고,  `lineTo`를  하면  직전위치부터  지정한  위치까지의  선을  그리며,  `moveTo`를  하면  `prevPos`만  갱신하고,  `setColor`를  하면  `Graphics2D`의  색을  설정합니다.  `clear`는 화면을 검은색으로 다시 칠합니다.
