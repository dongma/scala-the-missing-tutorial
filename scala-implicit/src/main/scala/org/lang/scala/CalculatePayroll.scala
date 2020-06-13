package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/12
 * scala中的虚类型，implicit必须出现在参数列表的最左边，而且只能出现一次
 */
object CalculatePayroll {

  def main(args: Array[String]): Unit = {
    val employee = Employee("Buck Trends", 100000.0F, 0.25F, 200F, 0.10F, 0.05F)
    val pay1 = Payroll start employee
    // 401k和保险扣除的顺序可以交换
    val pay2 = Payroll minus401k pay1
    val pay3 = Payroll minusInsurance pay2
    val pay4 = Payroll minusTax pay3
    val pay = Payroll minusFinalDeductions pay4

    val twoWeekGross = employee.annualSalary / 26.0F
    val twoWeekNet = pay.netPay
    val percent = (twoWeekNet / twoWeekGross) * 100
    println(s"For ${employee.name}, the gross vs. net pay every 2 weeks is: ")
    println(f" $$${twoWeekGross}%.2f vs. $$${twoWeekNet}%.2f or ${percent}%.1f%%")

    import Pipeline._
    import Payroll._
    // pay1 |> Payroll.minus401k转化之后形成表达式Payroll.minus401k(pay1)
    val payPipeline = start(employee) |>
      minus401k |>
      minusInsurance |>
      minusTax |>
      minusFinalDeductions
    val percentPipeline = (twoWeekNet / twoWeekGross) * 100
    val twoWeekNetPipeline = payPipeline.netPay
    val twoWeekGrossPipeline = employee.annualSalary / 26.0F
    println(f" $$${twoWeekGrossPipeline}%.2f vs. $$${twoWeekNetPipeline}%.2f or ${percentPipeline}%.1f%%")

    // 使用zip方法将keys和values集合拼接起来，生成Map类型
    val keys = List("a", "b", "c", "d")
    val values = List("A", 123, 3.14159)
    val keysValues = keys zip values
    println(s"zip operate with keys and values, result: $keysValues")
  }

}

case class Employee(
   name: String,
   annualSalary: Float,
   taxRate: Float,
   insurancePremiumsPerPayPeriod: Float,
   _401kDeductionRate: Float,
   postTaxDeductionRates: Float)

case class Pay[Step](employee: Employee, netPay: Float)

object Payroll {
  def start(employee: Employee): Pay[PreTaxDeductions] =
    Pay[PreTaxDeductions](employee, employee.annualSalary / 26.0F)

  def minusInsurance(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - (pay.employee.insurancePremiumsPerPayPeriod)
    pay copy (netPay = newNet)
  }

  def minus401k(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - (pay.employee._401kDeductionRate * pay.netPay)
    pay copy (netPay = newNet)
  }

  def minusTax(pay: Pay[PreTaxDeductions]): Pay[PostTaxDeductions] = {
    val newNet = pay.netPay - (pay.employee.taxRate * pay.netPay)
    pay copy (netPay = newNet)
  }

  def minusFinalDeductions(pay: Pay[PostTaxDeductions]): Pay[Final]={
    val newNet = pay.netPay - pay.employee.postTaxDeductionRates
    pay copy (netPay = newNet)
  }
}

case class PreTaxDeductions()

case class PostTaxDeductions()

case class Final()

object Pipeline {
  implicit class toPiped[V](value: V) {
    def |>[R](f: V => R) = f(value)
  }
}