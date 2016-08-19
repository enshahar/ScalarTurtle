#  ScalarTurtle

스칼라를  사용한  터틀  그래픽  

이  프로젝트는  [거북이를  살펴보는  13가지  방법](https://fsharpforfunandprofit.com/posts/13-ways-of-looking-at-a-turtle/)에서  힌트를  얻어서  시작한  것입니다.  물론  재미로.

##  oo  turtle

[객체 지향으로 구현한 거북이](https://github.com/enshahar/ScalarTurtle/tree/master/src/main/scala/com/enshahar/turtle/oo)는 객체 내부에 저장한 상태를 기반으로 움직입니다. 이렇게 만든 거북이를 조정하는 경우에는 다음과 같이 움직임을 순서대로 적어줘야 합니다.

```scala
turtle.On()
(1  to  360).foreach  {  _  =>
    (1  to  4).foreach  {  _  =>
        turtle.move(300)
        turtle.turn(90  deg)
    }
    turtle.setColor(Random.nextInt(256),  Random.nextInt(256),  Random.nextInt(256))
    turtle.turn(1  deg)
}
```

원한다면 `move`나 `turn` 등의 명령어가 `this`를 반환해서 메서드 연쇄(chaining) 기법을 사용할 수도 있었을 것입니다. 
그랬다면 코드가 다음과 비슷해졌겠지요.

```scala
turtle.On()
(1  to  360).foreach  {  _  =>
    (1  to  4).foreach  {  _  =>
        turtle.move(300)
              .turn(90  deg)
    }
    turtle.setColor(Random.nextInt(256),  Random.nextInt(256),  Random.nextInt(256))
          .turn(1  deg)
}
```

![실행결과](https://github.com/enshahar/ScalarTurtle/blob/master/%EC%8B%A4%ED%96%89%EA%B2%B0%EA%B3%BC%EC%BA%A1%EC%B2%98.PNG)

##  functional turtle

[함수형으로 구현한 거북이](https://github.com/enshahar/ScalarTurtle/tree/master/src/main/scala/com/enshahar/turtle/functional)는 상태를 별도의 케이스클래스로 만들어서 불변 객체에 담습니다. 따라서, 클라이언트는 매번 `move`등의 명령을 실행할 때마다 새로 생기는 상태 객체를 반환받아서 활용해야 합니다. 이를 편리하게 하기 위해 `|>`라는 파이프 연산자를 지원하게 만들었습니다. 
이를 활용하고, 고차함수를 사용해 `repeat`등의 함수를 구현하면 다음과 같이 거북이를 조종할 수 있습니다.

```scala
turtle |> on |> repeat(360){ (s:TurtleState) => s |> changeColorAndDrawRect |> turn(1 deg) } |> flush
```

