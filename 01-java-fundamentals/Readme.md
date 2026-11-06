# Java Fundamentals and OOP: A Practical Developer Guide

> Practical notes for Java developers, with explanations, examples, and
> interview-ready distinctions. Examples target Java 17+ unless
> mentioned otherwise.

## 1. Introduction to Java

### 1.1 History of Java

Java was developed at Sun Microsystems by James Gosling and his team.
The project started in 1991 under the name **Oak**, with the goal of
building software for electronic devices. It was later renamed **Java**
and released publicly in 1995.

Java became popular because developers could compile source code once
and run the resulting bytecode on different operating systems, provided
a compatible Java Virtual Machine (JVM) was available.

Oracle acquired Sun Microsystems in 2010 and became the steward of Java.
Java continues to evolve through regular releases and the Java Community
Process.

**Why this history matters:** Java was designed around portability and
reliability. Those goals still influence how Java applications are
compiled, deployed, and operated today.

### 1.2 Java Features and Benefits

  -----------------------------------------------------------------------
  Feature                             What it means in practice
  ----------------------------------- -----------------------------------
  Platform-independent                Java source is compiled into
                                      bytecode. A compatible JVM can run
                                      that bytecode on different
                                      operating systems.

  Object-oriented                     Applications can model business
                                      concepts using classes, objects,
                                      interfaces, and composition.

  Strongly and statically typed       Types are checked by the compiler,
                                      helping catch many mistakes before
                                      runtime.

  Automatic memory management         The garbage collector reclaims heap
                                      memory occupied by objects that are
                                      no longer reachable.

  Robust                              Exception handling, type checking,
                                      and runtime checks help build
                                      reliable applications.

  Secure                              The platform provides bytecode
                                      verification, access controls, and
                                      security-related APIs. Application
                                      security still depends on good
                                      design and configuration.

  Multithreaded                       Java provides threads, concurrency
                                      utilities, executors, and
                                      synchronization tools.

  High-performance                    The JVM can profile running code
                                      and use Just-In-Time (JIT)
                                      compilation to optimize frequently
                                      executed code.

  Rich ecosystem                      Frameworks such as Spring, build
                                      tools such as Maven and Gradle, and
                                      mature libraries speed up
                                      development.
  -----------------------------------------------------------------------

**Important:** "Write once, run anywhere" does not mean every Java
application is automatically portable. Applications can still depend on
operating-system features, native libraries, file paths, environment
variables, or specific runtime versions.

### 1.3 JVM, JRE, and JDK

These three terms are related, but they are not interchangeable.

#### JVM --- Java Virtual Machine

The **JVM is the runtime engine that executes Java bytecode**. The Java
compiler does not normally generate operating-system-specific machine
code directly. It generates bytecode in `.class` files, and the JVM
executes that bytecode on the target machine.

A simplified execution flow:

``` text
Hello.java
    |
    | javac (Java compiler)
    v
Hello.class (bytecode)
    |
    | JVM
    v
Operating-system / hardware instructions
    |
    v
Program output
```

The JVM is responsible for more than simply running instructions. Its
major responsibilities include:

1.  **Class loading:** Locates and loads required class files into the
    runtime.
2.  **Bytecode verification:** Checks bytecode for structural and safety
    rules before execution.
3.  **Memory management:** Organizes runtime memory for objects, method
    execution, and class metadata.
4.  **Execution:** Interprets bytecode and uses JIT compilation to turn
    frequently executed code into optimized native machine code.
5.  **Garbage collection:** Identifies objects that are no longer
    reachable and reclaims their heap memory.
6.  **Thread management and synchronization:** Provides runtime support
    for Java threads and monitors.

##### Main JVM runtime memory areas

  -----------------------------------------------------------------------
  Memory area             Purpose                 Example
  ----------------------- ----------------------- -----------------------
  Heap                    Stores objects and      `new Employee()`
                          arrays; shared by       
                          threads                 

  Java Stack              Each thread has its own A method's local
                          stack of method frames, `int count`
                          local variables, and    
                          intermediate results    

  Method Area             Logical JVM area for    Class metadata and
                          class-level structures  method information
                          and runtime metadata    

  Runtime Constant Pool   Per-class runtime       String and method
                          representation of       references
                          constants and symbolic  
                          references              

  PC Register             Each thread tracks the  Current bytecode
                          current JVM instruction instruction
                          being executed          

  Native Method Stack     Supports execution of   JNI/native calls
                          native methods,         
                          depending on JVM        
                          implementation          
  -----------------------------------------------------------------------

**Practical example:**

``` java
public class MemoryDemo {

    public static void main(String[] args) {
        int number = 10;
        Employee employee = new Employee("Arun");

        System.out.println(employee.getName());
    }
}

class Employee {
    private final String name;

    Employee(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
```

Conceptually: - `employee` is a local reference held in the `main()`
method's stack frame. - The `Employee` object is allocated on the
heap. - The `name` field is part of that object. - The JVM manages the
object's lifetime. When the object is no longer reachable, it becomes
eligible for garbage collection; collection is not guaranteed to happen
immediately.

##### Class loading and execution

The class-loading process is commonly described in three broad stages:

-   **Loading:** The JVM finds the class definition and creates its
    runtime representation.
-   **Linking:** The JVM verifies bytecode, prepares class-level
    storage, and resolves symbolic references as required.
-   **Initialization:** Class initialization code runs, including static
    field initializers and static blocks.

The execution engine can interpret bytecode and compile frequently
executed code through the JIT compiler. This is one reason a
long-running Java service can optimize its performance as it runs.

#### JRE --- Java Runtime Environment

The **JRE is the runtime environment required to run Java
applications**. In the traditional Java packaging model, it contains: -
The JVM. - Core Java class libraries. - Supporting runtime files and
components.

A user who only needs to run a Java application traditionally needs a
JRE, not the full development kit.

**Modern Java note:** Since Java 9, the old separate JRE download is no
longer the standard Oracle JDK distribution model. Modern deployments
commonly use a JDK runtime image or a custom runtime image created with
tools such as `jlink`. Vendors may still provide runtime-only
distributions.

#### JDK --- Java Development Kit

The **JDK is the development kit used to write, compile, debug,
document, and package Java applications**. It includes the runtime
components needed to run Java code, along with developer tools.

Common JDK tools:

  Tool        Purpose
  ----------- --------------------------------------------------------
  `javac`     Compiles `.java` source files into `.class` bytecode
  `java`      Launches a Java application
  `jshell`    Interactive Java REPL for trying code snippets
  `javadoc`   Generates API documentation from source comments
  `jar`       Creates and manages JAR files
  `jdb`       Command-line debugger
  `javap`     Disassembles class files and displays bytecode details
  `jlink`     Creates a custom runtime image from modules

#### JDK vs JRE vs JVM --- quick comparison

  -----------------------------------------------------------------------
  Component               Main responsibility     Typical user
  ----------------------- ----------------------- -----------------------
  JVM                     Executes bytecode       Every running Java
                                                  application

  JRE                     Provides the runtime    Application operator /
                          environment             end user

  JDK                     Provides development    Java developer / build
                          tools and runtime       server
                          components              
  -----------------------------------------------------------------------

A useful conceptual relationship is:

``` text
JDK
├── Development tools (javac, javadoc, jar, jdb, ...)
└── Runtime components
    ├── JVM
    └── Java libraries and supporting files
```

This is a conceptual view, not a guarantee about the exact directory
layout of every modern JDK distribution.

**Real-world example:** On a developer laptop or CI build agent, install
a JDK because source code must be compiled and tested. In production,
deploy a suitable runtime image containing the required Java runtime
components. Some organizations simply deploy a full JDK image for
operational consistency.

### 1.4 Getting Started with Java

1.  Install a JDK, such as JDK 17 or JDK 21.
2.  Verify the installation:

``` bash
java -version
javac -version
```

3.  Create a file named `Hello.java`:

``` java
public class Hello {

    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

4.  Compile and run:

``` bash
javac Hello.java
java Hello
```

Output:

``` text
Hello, Java!
```

**Understand the entry point:** - `public`: The JVM can access the
method from outside the class. - `static`: The method can be called
without creating an object of `Hello`. - `void`: The method does not
return a value. - `String[] args`: Receives command-line arguments.

------------------------------------------------------------------------

## 2. Java Fundamentals

### 2.1 Variables and Data Types

A variable is a named storage location. Java is statically typed, so a
variable's type is known at compile time.

``` java
int age = 35;
long accountNumber = 123456789L;
double salary = 85000.50;
char grade = 'A';
boolean active = true;
String name = "Arun";
```

#### Primitive data types

Java has eight primitive types:

  ---------------------------------------------------------------------------
  Type                    Example                     Typical use
  ----------------------- --------------------------- -----------------------
  `byte`                  `byte status = 1;`          Small integer values

  `short`                 `short year = 2026;`        Small-range integer
                                                      values

  `int`                   `int count = 100;`          General integer
                                                      calculations

  `long`                  `long id = 123456789L;`     Large integer values

  `float`                 `float rate = 2.5F;`        Single-precision
                                                      decimal values

  `double`                `double price = 99.99;`     General floating-point
                                                      calculations

  `char`                  `char initial = 'A';`       One UTF-16 code unit

  `boolean`               `boolean enabled = true;`   True/false conditions
  ---------------------------------------------------------------------------

`String`, arrays, and objects are reference types, not primitives.

**Primitive vs reference type:**

``` java
int first = 10;
int second = first;
second = 20;

System.out.println(first);  // 10

Employee one = new Employee("Arun");
Employee two = one;
two.setName("Rahul");

System.out.println(one.getName()); // Rahul
```

The primitive assignment copies the value. The reference assignment
copies the reference, so both variables refer to the same object.

### 2.2 Operators and Expressions

An operator performs an operation; an expression combines values,
variables, and operators to produce a result.

``` java
int a = 10;
int b = 3;

// Arithmetic
int sum = a + b;           // 13
int remainder = a % b;     // 1

// Relational
boolean greater = a > b;   // true

// Logical (short-circuit)
boolean valid = a > 0 && b > 0;

// Assignment
a += 5;                    // a becomes 15

// Ternary
String result = a >= 10 ? "Valid" : "Invalid";
```

Common operator groups: - Arithmetic: `+`, `-`, `*`, `/`, `%` -
Relational: `==`, `!=`, `>`, `<`, `>=`, `<=` - Logical: `&&`, `||`,
`!` - Assignment: `=`, `+=`, `-=`, `*=` - Unary: `++`, `--`, `!` -
Bitwise: `&`, `|`, `^`, `~` - Shift: `<<`, `>>`, `>>>` - Ternary:
`condition ? value1 : value2`

**Developer tip:** Integer division truncates the fractional part. For
example, `10 / 3` returns `3`, while `10.0 / 3` returns a floating-point
result.

### 2.3 Control Flow Statements

Control-flow statements decide which code runs and how often it runs.

#### if-else

Use `if-else` when execution depends on a condition.

``` java
int marks = 76;

if (marks >= 80) {
    System.out.println("Distinction");
} else if (marks >= 60) {
    System.out.println("First division");
} else {
    System.out.println("Needs improvement");
}
```

#### switch

Use `switch` when one expression is compared against multiple known
cases.

``` java
int statusCode = 2;

switch (statusCode) {
    case 1 -> System.out.println("CREATED");
    case 2 -> System.out.println("PROCESSING");
    case 3 -> System.out.println("COMPLETED");
    default -> System.out.println("UNKNOWN");
}
```

The arrow-style switch syntax is available in modern Java. Traditional
`case` syntax is also valid.

#### Loops

Use loops to repeat a block of code.

``` java
// for: when the number of iterations is known
for (int i = 1; i <= 3; i++) {
    System.out.println(i);
}

// while: checks the condition before each iteration
int count = 1;
while (count <= 3) {
    System.out.println(count++);
}

// do-while: executes at least once
int attempt = 1;
do {
    System.out.println("Attempt " + attempt);
    attempt++;
} while (attempt <= 3);

// enhanced for: convenient for arrays and collections
int[] numbers = {10, 20, 30};
for (int number : numbers) {
    System.out.println(number);
}
```

Use `break` to exit a loop and `continue` to skip the current iteration.

### 2.4 Arrays

An array stores a fixed number of elements of the same type. Its indexes
start at `0`, and its length is available through the `length` field.

``` java
int[] salaries = {30000, 45000, 60000};

System.out.println(salaries[0]);      // 30000
System.out.println(salaries.length);  // 3

int total = 0;
for (int salary : salaries) {
    total += salary;
}

System.out.println(total); // 135000
```

A two-dimensional array is an array whose elements are themselves
arrays:

``` java
int[][] matrix = {
    {1, 2},
    {3, 4}
};

System.out.println(matrix[1][0]); // 3
```

**Important:** Accessing an invalid index causes
`ArrayIndexOutOfBoundsException`. Use arrays when the size is fixed; use
collections such as `ArrayList` when the number of elements needs to
change.

### 2.5 Strings

`String` represents text and is immutable. Once a String object is
created, its contents cannot be changed. Operations such as
concatenation return a new String.

``` java
String language = "Java";
String fullName = language.concat(" Backend");

System.out.println(fullName);          // Java Backend
System.out.println(language);          // Java
System.out.println(language.length()); // 4
System.out.println(language.equals("Java")); // true
```

#### `==` vs `equals()`

-   `==` compares whether two references point to the same object.
-   `equals()` compares content when the class implements it
    appropriately; `String` does.

``` java
String first = new String("Java");
String second = new String("Java");

System.out.println(first == second);      // false
System.out.println(first.equals(second)); // true
```

#### StringBuilder

Use `StringBuilder` when repeatedly modifying text, such as building a
large message in a loop.

``` java
StringBuilder builder = new StringBuilder();

builder.append("Java");
builder.append(" ");
builder.append("Developer");

System.out.println(builder); // Java Developer
```

`StringBuilder` is mutable and is not synchronized. For shared
concurrent modification, consider `StringBuffer` or a suitable
synchronization strategy.

------------------------------------------------------------------------

## 3. Object-Oriented Programming (OOP) Concepts

OOP organizes software around objects that combine state (data) and
behavior (methods). It helps represent business concepts, control access
to data, and make systems easier to extend and test.

### 3.1 Classes and Objects

A **class** defines the fields and behavior that its objects have. An
**object** is a runtime instance of a class.

``` java
class Employee {
    private String name;
    private String department;

    Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    void work() {
        System.out.println(name + " works in " + department);
    }
}

class Main {
    public static void main(String[] args) {
        Employee employee =
                new Employee("Arun", "Engineering");

        employee.work();
    }
}
```

Here: - `Employee` is the class. - `employee` is a reference variable. -
`new Employee(...)` creates an object. - The constructor initializes the
object's state. - `work()` defines its behavior.

### 3.2 Encapsulation

Encapsulation combines data and behavior in a class and restricts direct
access to internal state. A class exposes controlled methods so it can
protect its own rules.

**Real-world example:** A bank account should not allow another class to
set its balance to an arbitrary negative value.

``` java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit must be positive");
        }
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

``` java
BankAccount account = new BankAccount();
account.deposit(5000);

System.out.println(account.getBalance()); // 5000
```

Why it matters: - Prevents uncontrolled modification of fields. - Keeps
validation close to the data it protects. - Reduces dependencies on
internal implementation. - Makes future changes safer.

**Best practice:** Keep fields private and expose methods that express
valid business operations rather than creating setters for every field
automatically.

### 3.3 Inheritance

Inheritance allows a class to acquire and extend the accessible behavior
of another class. It represents an **is-a** relationship.

``` java
class Employee {
    protected String name;

    Employee(String name) {
        this.name = name;
    }

    void work() {
        System.out.println(name + " is working");
    }
}

class Developer extends Employee {

    Developer(String name) {
        super(name);
    }

    void writeCode() {
        System.out.println(name + " is writing Java code");
    }
}
```

``` java
Developer developer = new Developer("Arun");
developer.work();      // inherited method
developer.writeCode(); // child-specific method
```

Key points: - `extends` establishes class inheritance. - `super()` calls
a parent constructor. - A child class can override an inherited instance
method. - Java supports single inheritance for classes: a class can
directly extend only one class. - Java supports multiple inheritance of
type through interfaces.

**Design tip:** Prefer composition when the relationship is not
genuinely "is-a". For example, a `Car` has an `Engine`; a car is not an
engine.

### 3.4 Polymorphism

Polymorphism means that the same parent type or method contract can
represent different implementations. It helps write code that depends on
abstractions instead of concrete classes.

#### Runtime polymorphism --- method overriding

The method implementation is selected at runtime based on the actual
object.

``` java
class Payment {
    void pay(double amount) {
        System.out.println("Generic payment: " + amount);
    }
}

class UpiPayment extends Payment {
    @Override
    void pay(double amount) {
        System.out.println("UPI payment: " + amount);
    }
}

class CardPayment extends Payment {
    @Override
    void pay(double amount) {
        System.out.println("Card payment: " + amount);
    }
}
```

``` java
Payment payment = new UpiPayment();
payment.pay(1500); // UPI payment: 1500.0

payment = new CardPayment();
payment.pay(2500); // Card payment: 2500.0
```

The variable type is `Payment`, but the actual object determines which
overridden method executes.

#### Compile-time polymorphism --- method overloading

Methods share a name but have different parameter lists. The compiler
selects the matching overload.

``` java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

**Remember:** Changing only the return type does not create a valid
overload. The parameter list must differ.

### 3.5 Abstraction

Abstraction exposes what an object can do while hiding the details of
how it does it. In Java, abstract classes and interfaces are common
tools for defining abstractions.

``` java
abstract class Payment {
    abstract void pay(double amount);

    void printReceipt() {
        System.out.println("Receipt generated");
    }
}

class UpiPayment extends Payment {
    @Override
    void pay(double amount) {
        System.out.println("Paid " + amount + " through UPI");
    }
}
```

``` java
Payment payment = new UpiPayment();
payment.pay(1000);
payment.printReceipt();
```

Key points: - An abstract class cannot be instantiated directly. - It
can contain abstract methods and implemented methods. - A concrete
subclass must implement inherited abstract methods unless it is also
abstract. - Abstraction reduces the amount of implementation detail a
caller needs to understand.

### 3.6 Interfaces

An interface defines a contract. A class that implements it provides the
required behavior. Interfaces are useful when unrelated classes need to
support the same capability.

``` java
interface NotificationService {
    void send(String message);
}

class EmailNotification implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}

class SmsNotification implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }
}
```

A service can depend on the interface rather than a specific
notification provider:

``` java
class AlertService {
    private final NotificationService notificationService;

    AlertService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    void alert(String message) {
        notificationService.send(message);
    }
}
```

``` java
AlertService service =
        new AlertService(new EmailNotification());

service.alert("Server CPU usage is high");
```

Important points: - A class can implement multiple interfaces. -
Interfaces can declare abstract methods. - Interfaces can also contain
`default` and `static` methods. - Since Java 9, interfaces can also
contain private helper methods. - Interface fields are implicitly
`public static final`.

#### Abstract class vs interface

  -----------------------------------------------------------------------
  Abstract class                      Interface
  ----------------------------------- -----------------------------------
  A class extends one abstract class  A class can implement multiple
                                      interfaces

  Can have instance fields and        Cannot have ordinary per-instance
  constructors                        fields or constructors

  Can provide shared state and        Primarily defines a contract and
  implementation                      capabilities

  Useful for closely related classes  Useful for a capability shared by
  sharing a base                      different types
  -----------------------------------------------------------------------

### 3.7 Packages

A package groups related classes and interfaces. It helps organize a
codebase, control access, and avoid class-name conflicts.

Example project structure:

``` text
src/
└── com/
    └── example/
        ├── model/
        │   └── Employee.java
        └── service/
            └── EmployeeService.java
```

`Employee.java`:

``` java
package com.example.model;

public class Employee {
    private final String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

`EmployeeService.java`:

``` java
package com.example.service;

import com.example.model.Employee;

public class EmployeeService {
    public void printEmployee() {
        Employee employee = new Employee("Arun");
        System.out.println(employee.getName());
    }
}
```

The package declaration should be the first non-comment statement in the
source file. The directory structure conventionally matches the package
name.

------------------------------------------------------------------------

## 4. SOLID Principles

SOLID is a set of five object-oriented design principles that help make
software easier to maintain, extend, test, and understand. They are
guidelines, not rigid rules; apply them where they reduce coupling and
improve design.

### 4.1 S --- Single Responsibility Principle (SRP)

**A class should have one clear responsibility, or one primary reason to
change.**

A class that calculates salaries, saves employees to a database, and
sends emails has several unrelated responsibilities. Changes to email or
database logic can affect the same class.

**Avoid:**

``` java
class EmployeeService {
    void calculateSalary() {
        System.out.println("Calculating salary");
    }

    void saveToDatabase() {
        System.out.println("Saving employee");
    }

    void sendEmail() {
        System.out.println("Sending email");
    }
}
```

**Prefer:**

``` java
class SalaryCalculator {
    void calculate() {
        System.out.println("Calculating salary");
    }
}

class EmployeeRepository {
    void save() {
        System.out.println("Saving employee");
    }
}

class EmailService {
    void send() {
        System.out.println("Sending email");
    }
}
```

Each class now has a focused responsibility.

### 4.2 O --- Open/Closed Principle (OCP)

**Software entities should be open for extension but closed for
modification.**

You should be able to add a new behavior without repeatedly changing
stable, tested code.

**Avoid:** A payment service with a growing `if-else` or `switch` for
every payment type.

**Prefer:** Define a common contract and add implementations.

``` java
interface PaymentMethod {
    void pay(double amount);
}

class UpiPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}

class CardPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Paid using Card: " + amount);
    }
}

class PaymentService {
    void process(PaymentMethod method, double amount) {
        method.pay(amount);
    }
}
```

To add `WalletPayment`, create another implementation. `PaymentService`
does not need a new conditional branch.

### 4.3 L --- Liskov Substitution Principle (LSP)

**An object of a child type should be usable wherever its parent type is
expected without breaking the correctness of the program.**

A subtype must honor the behavioral expectations of its parent type.

**Problem example:** If a general `Bird` class promises that every bird
can fly, a `Penguin` subtype breaks that promise.

**Better design:**

``` java
interface Bird {
    void eat();
}

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void eat() {
        System.out.println("Sparrow eats");
    }

    public void fly() {
        System.out.println("Sparrow flies");
    }
}

class Penguin implements Bird {
    public void eat() {
        System.out.println("Penguin eats");
    }
}
```

Now code that requires flying accepts `FlyingBird`, not every `Bird`.

### 4.4 I --- Interface Segregation Principle (ISP)

**Clients should not be forced to depend on methods they do not use.**

Prefer small, focused interfaces over one large interface containing
unrelated operations.

**Avoid:**

``` java
interface Machine {
    void print();
    void scan();
    void fax();
}
```

A basic printer would be forced to implement scan and fax methods.

**Prefer:**

``` java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class BasicPrinter implements Printer {
    public void print() {
        System.out.println("Printing document");
    }
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print() {
        System.out.println("Printing document");
    }

    public void scan() {
        System.out.println("Scanning document");
    }
}
```

Each class implements only the capabilities it supports.

### 4.5 D --- Dependency Inversion Principle (DIP)

**High-level modules should depend on abstractions, not directly on
low-level implementation details.**

Business logic should not be tightly coupled to a specific database,
email provider, or external service.

**Avoid:**

``` java
class EmailService {
    void send(String message) {
        System.out.println("Email: " + message);
    }
}

class OrderService {
    private final EmailService emailService = new EmailService();

    void placeOrder() {
        emailService.send("Order placed");
    }
}
```

`OrderService` creates a concrete dependency itself, making replacement
and isolated testing harder.

**Prefer:**

``` java
interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SmsSender implements MessageSender {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

class OrderService {
    private final MessageSender messageSender;

    OrderService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    void placeOrder() {
        messageSender.send("Order placed");
    }
}
```

``` java
OrderService orderService =
        new OrderService(new EmailSender());

orderService.placeOrder();
```

The dependency is supplied from outside (constructor injection). In
Spring, the container commonly performs this dependency injection.

### SOLID --- Quick Revision

  -----------------------------------------------------------------------
  Principle               Meaning                 Design question
  ----------------------- ----------------------- -----------------------
  SRP                     Single Responsibility   Does this class have
                                                  unrelated reasons to
                                                  change?

  OCP                     Open/Closed             Can I add behavior
                                                  without modifying
                                                  stable code?

  LSP                     Liskov Substitution     Can a subtype safely
                                                  replace its parent?

  ISP                     Interface Segregation   Are clients forced to
                                                  implement methods they
                                                  do not need?

  DIP                     Dependency Inversion    Does business logic
                                                  depend on an
                                                  abstraction rather than
                                                  a concrete detail?
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 5. Quick Revision and Interview Questions

### Important distinctions

  -----------------------------------------------------------------------
  Topic                               Remember
  ----------------------------------- -----------------------------------
  JDK / JRE / JVM                     Development kit / runtime
                                      environment / bytecode execution
                                      engine

  Heap / Stack                        Objects and arrays / per-thread
                                      method frames and local variables

  Primitive / Reference               Value stored directly / reference
                                      to an object or array

  Array / ArrayList                   Fixed length / resizable collection

  `==` / `equals()`                   Reference identity / logical
                                      equality as defined by the class

  `String` / `StringBuilder`          Immutable / mutable

  Overloading / Overriding            Same method name with different
                                      parameters / subtype replaces
                                      inherited behavior

  Abstract class / Interface          Shared base state and behavior /
                                      contract and capabilities

  Inheritance / Composition           Is-a relationship / has-a
                                      relationship
  -----------------------------------------------------------------------

### Interview practice

1.  Explain the complete lifecycle of a Java program from `.java` source
    to execution.
2.  What is the difference between JDK, JRE, and JVM?
3.  What are the main JVM memory areas?
4.  What happens when an object becomes eligible for garbage collection?
5.  What is the difference between stack memory and heap memory?
6.  Why is Java platform-independent? What are the limitations of that
    claim?
7.  What is the difference between primitive and reference variables?
8.  Why is String immutable? When would you use StringBuilder?
9.  Explain method overloading and overriding with a real example.
10. What is the difference between abstraction and encapsulation?
11. When would you choose an interface over an abstract class?
12. Explain all five SOLID principles using practical examples.
13. Why is constructor injection useful for loose coupling and unit
    testing?
14. When should composition be preferred over inheritance?

## 6. Suggested Practice Tasks

1.  Create an `Employee` class with private fields, constructor,
    getters, and validation.
2.  Create `PaymentMethod` implementations for UPI and card payments.
3.  Use a parent reference to call overridden methods on different child
    objects.
4.  Refactor a class that handles salary calculation, database
    persistence, and email into focused classes.
5.  Create a `NotificationService` that accepts an interface and can
    switch between email and SMS implementations.
6.  Design a small order-processing example that follows SRP, OCP, ISP,
    and DIP.
