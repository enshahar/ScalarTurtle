import scala.annotation.tailrec

val result = Map(
  (0,0) -> 0,
  (0,1) -> 1,
  (0,2) -> -1,
  (1,0) -> -1,
  (1,1) -> 0,
  (1,2) -> 1,
  (2,0) -> 1,
  (2,1) -> -1,
  (2,2) -> 0)

val rnd1 = new scala.util.Random()
val rnd2 = new scala.util.Random()
def gbb() = rnd1.nextInt(3)
def gb() = rnd2.nextInt(3)

@tailrec
def run(n:Int, y:(Int,Int,Int)):(Int,Int,Int) =
  if(n == 0)  y
  else {
    val r = result(gbb(), gb())
    if(r<0)
      run(n-1, (y._1+1,y._2,y._3))
    else if(r==0)
      run(n-1, (y._1,y._2+1,y._3))
    else
      run(n-1, (y._1,y._2,y._3+1))
  }

run(10000,(0,0,0))


