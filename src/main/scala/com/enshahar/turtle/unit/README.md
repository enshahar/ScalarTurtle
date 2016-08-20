# 단위(unit) 변환 예제

각도의 단위로는 라디안, 도, 분, 초가 있습니다. 수학적으로는 다음과 같은 관계가 있죠.

- 360도 = 2*pi 라디안
- 1도 = 60분
- 1분 = 60초

## 각도 단위 변환 클래스

각도 단위 사이의 변환을 위해 `AngleUnit`라는 추상 클래스를 만듭니다. 혹시 `case`문에서 사용할지도 모르고, 
새로운 단위 추가시에도 규칙을 지켜야 하므로 `sealed`로 만들어서 외부에서의 예기치 않은 변경을 방지합니다.

```scala
sealed abstract class AngleUnit {
```

단위 사이의 변환을 위해 1단위를 변환하는 기본 값을 정의합니다. 다른 모든 변환은 이 값을 중심으로 이뤄집니다. 
단위의 이름은 `name`이라는 필드에 들어갑니다.

```scala
  val toRadian: Double
  val toDegree: Double
  val toMinute: Double
  val toSecond: Double
  
  val name: String
```

어떤 단위로 주어진 `value` 값을 변환하는 경우에는 `to단위`라는 1단위 변환값에 `value`를 곱하면 됩니다.
거꾸로 어떤 단위에서 가져오는 경우에는 그 반대로 `to단위`로 `value`를 나눠줍니다.

임의의 단위로 변환하는 `convert` 함수는 `AngleUnit` 동반객체에 있는 `convert` 메서드를 호출해서 해결합니다.

각도의 경우 `360`도나 `2*pi` 라디안을 기준으로 정규화시킬 수 있습니다. 정규화를 위한 `normalize` 메서드를 정의합니다.

```scala
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
```

동반객체에서는 임의의 두 각도 단위 사이의 변환을 담당하는 `convert` 함수를 정의합니다.

```scala
object AngleUnit {
  def convert(value: Double, from: AngleUnit, to: AngleUnit): Double = to match {
    case RADIAN => from.toRadians(value)
    case DEGREE => from.toDegrees(value)
    case MINUTE => from.toMinutes(value)
    case SECOND => from.toSeconds(value)
  }
```

또, 문자열에 해당하는 단위를 돌려주는 `apply` 함수도 정의합니다(어디 써먹지는 않네요 ^^).

```scala
  def apply(name: String): AngleUnit = name match {
    case "radian" => RADIAN
    case "degree" => DEGREE
    case "minute" => MINUTE
    case "second" => SECOND
  }
}
```

이제, 각 단위별로 싱글턴 객체를 하나씩 만듭니다. 
이름 `name`과 1 단위에 대한 변환값 `toRadian`, `toDegree`, `toMinute`, `toSecond`를 정의하고, 
정규화 메서드 `normalize`도 정의합니다.

```scala
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

## 각도 클래스

각도를 표현하는 클래스입니다. 
값 `v`와 단위 `unit`을 받아서 생성되며, 필요에 따라 `AngleUnit`에 있는 메서드를 활용해서 값 변환을 수행합니다.
또한, `+` 이항 연산을 제공합니다.

```scala
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
```

동반객체에서는 값과 단위(문자열 또는 `AngleUnit`타입의 값)를 가지고 `Angle` 객체를 만들어주는 
팩토리 메서드를 제공합니다.

```scala
object Angle {
  def apply(value: Double, unit: AngleUnit) = new Angle(value, unit)
  def apply(value: Double, unit: String) = new Angle(value, AngleUnit(unit))
}
```

하지만, 각도 객체 생성시 `new Angle(10.0, DEGREE)`를 사용하는 것이나, 
`Angle(10.0, "degree")`를 사용하는 것 모두 불편하기는 마찬가지입니다. 
편하게 `10.0 deg`나 `10 radians`등을 사용할 수 있으면 좋겠죠. 
문제는, `10.0`이나 `10`과 같은 다른 타입의 값에 대해 `deg`와 같은 후위 연산자를 어떻게 추가할 수 있느냐입니다.
이럴 때 스칼라의 암시적 클래스를 활용합니다.

먼저 후위 연산자가 정의된 트레이트를 하나 만듭니다. 후위 연산자에 따라 적절한 단위의 함수를 호출해야 하기 때문에, 
이 트레이트를 믹스인한 클래스가 정의해야 하는 추상 메서드 `angleIn`을 하나 정의하고, 나머지 후위 연산자들은 
`angleIn` 추상 메서드를 활용해서 정의합니다.

```scala
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
```

이제 `angleIn`에서는 `unit`에 따라 적절한 `Angle` 객체를 만들어 반환하면 됩니다.
`Int`, `Long`, `Double`에 대해 후위 연산자들을 제공하기 위해서 다음과 같이 암시적 클래스를 만듭니다. 
이런 암시적 클래스 정의를 `com.enshahar.turtle.unit`에서 가져올 수 있게 `package.scala`안의 
패키지 객체에 넣어 둡니다.

```scala
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
```
