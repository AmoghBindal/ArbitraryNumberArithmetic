# MyInfArith

**MyInfArith** is a Java-based command-line tool for arbitrary-precision arithmetic operations on integers and floating-point numbers. It supports addition, subtraction, multiplication, and division.

## 📁 Project Structure

```
.
├── build.xml
├── MyInfArith.java
└── arbitraryarithmetic
    ├── AInteger.java
    └── AFloat.java
```

- `MyInfArith.java`: Main entry point that parses input and invokes arithmetic operations.
- `arbitraryarithmetic/AInteger.java`: Implements arbitrary-precision integers.
- `arbitraryarithmetic/AFloat.java`: Implements arbitrary-precision floating-point numbers.
- `build.xml`: Apache Ant build script.

## ⚙️ Requirements

- Java 8 or higher
- Apache Ant

## 🛠️ Building the Project

To compile and package the project using Apache Ant:

```bash
ant jar
```

This command will compile the source code and generate a JAR file at:

```
dist/MyInfArith.jar
```

## 🚀 Usage

Run the program using the following command:

```bash
java -jar dist/MyInfArith.jar <type> <operation> <operand1> <operand2>
```

### Parameters

- `<type>`: `int` or `float`
- `<operation>`: `add`, `sub`, `mul`, or `div`
- `<operand1>`, `<operand2>`: String representations of the numbers

### Examples

```bash
java -jar dist/MyInfArith.jar int add 123456789123456789 987654321987654321
```

```bash
java -jar dist/MyInfArith.jar float div 3.14159 2.0
```
