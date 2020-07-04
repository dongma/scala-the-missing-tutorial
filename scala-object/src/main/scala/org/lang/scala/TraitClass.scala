package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/28
 * scala中trait接口特征，混入trait其与abstract class的区分
 */
object TraitClass {

  def main(args: Array[String]): Unit = {
    val button = new ObservableButton("click me")
    val buttonObserver = new ButtonCountObserver
    // 将buttonObserver添加到点击事件的观察者列表里 observers，每调用一次click button.count就会+1
    button addObserver buttonObserver
    (1 to 5) foreach (_ => button.click())
    assert(buttonObserver.count == 5)

    // 在创建Button对象时 默认实现了ObservableClicks trait接口，并可在button2上注册事件
    val button2 = new Button("click me") with ObservableClicks
    val clickObserver = new ClickCountObserver()
    button2 addObserver clickObserver
    (1 to 5) foreach (_ => button2.click())
    assert(clickObserver.count == 5, s"clickObserver.count ${clickObserver.count} != 5")
  }

}

/*
 * 在java8中可以在接口中定义方法，其被称为默认方法defender method, java8中的接口只能定义静态字段，
 * 而scala中的trait则可以定义实例级字段
 */
trait Observer[-State] {
  def receiveUpdate(state: State): Unit
}

trait Subject[State] {
  private var observers: List[Observer[State]] = Nil

  def addObserver(observer: Observer[State]): Unit =
    observers ::= observer

  def notifyObservers(state: State): Unit =
    observers foreach (_.receiveUpdate(state))
}

trait PureAbstractTrait {
  def abstractMember(string: String): Unit
}

/*
 * 包含抽象方法的trait并不需要声明为抽象类对象，无需在trait关键字之前添加abstract关键字。但是，
 * 那些包含了一个或多个为定义方法的class则必须声明为抽象类
 */
abstract class AbstractClass {
  def concreteMember(string: String): Int = string.length

  // abstract method抽象方法，其与java中抽象类定义相同 (存在抽象method的类为抽象类)
  def abstractMember(string: String): Int
}


/*
 * Widget是一个标记特征 ui层
 */
abstract class Widget

/*
 * 因为所有小组件都有click点击事件，故需要引入一个trait的Clickable
 */
trait Clickable {
  def click(): Unit = updateUi();

  protected def updateUi(): Unit = { /* 包含GUI样式的修改逻辑 */ }
}

/*
 * Button在原有基础上实现 Clickable，自定义updateUi()的逻辑
 */
class Button(val label:String) extends Widget with Clickable {

  override protected def updateUi(): Unit = { /* Button的GUI样式的修改逻辑 */ }
}

/*
 * Observation特征应该依附于Clickable特征，而不再是之前的Button类。由于确实需要观察像点击这样的事件，
 * 为此引入了仅用于观察Clickable事件的trait
 */
trait ObservableClicks extends Clickable with Subject[Clickable] {

  abstract override def click(): Unit = {
    super.click()
    notifyObservers(this)
  }
}

class ClickCountObserver extends Observer[Clickable] {
  var count = 0
  override def receiveUpdate(state: Clickable): Unit = count += 1
}

/*
 * ObservableButton类继承了Button类，并混入了Subject特征
 */
class ObservableButton(name: String) extends Button(name) with Subject[Button] {
  override def click(): Unit = {
    super.click()
    notifyObservers(this)
  }
}

/*
 * 定义Observer实现类，当观察者Observer发现事件后 执行update()更新方法
 */
class ButtonCountObserver extends Observer[Button] {
  var count = 0
  override def receiveUpdate(state: Button): Unit = count += 1
}