// Игра "Змейка"
// 21.08.2021

import processing.sound.*;
SoundFile start;
SoundFile beep;
SoundFile damage;

final int C_SIZE = 10;

class Point {
  public int _x;
  public int _y;
  
  Point(int x, int y) {
    this._x = x;
    this._y = y;
  }
};

class Snake
{
  public
  int _x;
  int _y;
  int _xspeed = 1;
  int _yspeed = 0;
  ArrayList<Point> _tail = new ArrayList<Point>();
  int _total = 5;
  
  
  Snake() {
    this._x = 32 * C_SIZE;
    this._y = 24 * C_SIZE;
  }
  
  void dir(int x, int y) {
    this._xspeed = x;
    this._yspeed = y;
  }
  
  void update() {
    _x = _x + _xspeed * C_SIZE;
    _y = _y + _yspeed * C_SIZE;
    
    _x = (_x < 0) ? width : _x;
    _x = (_x > width) ? 0 : _x;
    
    _y = (_y < 0) ? height : _y;
    _y = (_y > height) ? 0 : _y;
    
    snake._tail.add(0, new Point(this._x, this._y));
    if(snake._tail.size() > snake._total) {
      int it = snake._tail.size();
      snake._tail.remove(it - 1);
    }
    
    // Проверка на пересечение
    for(int i = 1; i < snake._tail.size(); i++) {
      Point seq = snake._tail.get(i);
      if(seq._x == this._x && seq._y == this._y) {
        this._total = i;
        println("game over");
        damage.play();
        
        for(int j = i; j < snake._tail.size(); j++)
        {
          this._tail.remove(j);
        }
      }
    }
  }
  
  void show() {
    
    fill(#086A00);
    rect(_x, _y, C_SIZE, C_SIZE);
    
    fill(#17EA05);
    for(int i = 1; i < snake._tail.size(); i++) {
      Point cur = snake._tail.get(i);
      rect(cur._x, cur._y, C_SIZE, C_SIZE);
    }
  }
};

class Target {
  int _x;
  int _y;
  
  Target() {    
    this._x = int(random(2, 63)) * C_SIZE;
    this._y = int(random(2, 47)) * C_SIZE;
  }
  
  void newPos() {
    this._x = int(random(2, 60)) * C_SIZE;
    this._y = int(random(2, 40)) * C_SIZE;
  }
  
  void show() {
    fill(#FF9203);
    rect(this._x, this._y, C_SIZE, C_SIZE);
    
    if (snake._x == this._x && snake._y == this._y) {
      newPos();
      count++;
      println(count);
      snake._total++;
      beep.play();
    }
  }
  
};

Snake snake = new Snake();
Target target = new Target();
int count = 0;

// Задание fps, цвета фона, и размеров окна
void setup() {
  size(640, 480);
  background(#FFFFFF);
  frameRate(15);
  
  beep = new SoundFile(this, "beep.mp3");
  start = new SoundFile(this, "start.mp3");
  damage = new SoundFile(this, "damage.mp3");
  start.play();
}

// Главный цикл отрисовки
void draw() {
  background(#FFFFFF);
   
  snake.update();
  snake.show();
  target.show();
}

// Флаги запрещающие движение
boolean b_up = false;
boolean b_dn = false;
boolean b_lt = true;
boolean b_rt = false;

// Контроль нажатия стрелок
void keyPressed() {
  if (keyCode == UP && !b_up) {
    snake.dir(0, -1);
    b_dn = true;
    b_lt = false;
    b_rt = false;
  }
  else if (keyCode == DOWN && !b_dn) {
    snake.dir(0, 1);
    b_up = true;
    b_lt = false;
    b_rt = false;
  }
  else if (keyCode == RIGHT && !b_rt) {
    snake.dir(1, 0);
    b_lt = true;
    b_up = false;
    b_dn = false;
  }
  else if (keyCode == LEFT && !b_lt) {
    snake.dir(-1, 0);
    b_rt = true;
    b_up = false;
    b_dn = false;
  }
  
}