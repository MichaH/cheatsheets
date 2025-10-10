# Java Functional Interfaces - Cheat Sheet

## Grundlagen
```java
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
```

## Die vier Hauptkategorien

| Kategorie | Interface | Methode | Parameter | Rückgabe | Zweck |
|-----------|-----------|---------|-----------|----------|-------|
| **Consumer** | `Consumer<T>` | `accept(T)` | T | void | Konsumiert Wert |
| **Supplier** | `Supplier<T>` | `get()` | - | T | Liefert Wert |
| **Function** | `Function<T,R>` | `apply(T)` | T | R | Transformiert Wert |
| **Predicate** | `Predicate<T>` | `test(T)` | T | boolean | Testet Bedingung |

## Consumer-Familie

### Consumer<T>
Konsumiert einen Wert, gibt nichts zurück
```java
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello World");

// Mit Method Reference
Consumer<String> printer2 = System.out::println;

// Verkettung
Consumer<String> upper = s -> System.out.println(s.toUpperCase());
Consumer<String> lower = s -> System.out.println(s.toLowerCase());
Consumer<String> both = upper.andThen(lower);
both.accept("Hello"); // Gibt "HELLO" und "hello" aus
```

### BiConsumer<T,U>
Konsumiert zwei Werte
```java
BiConsumer<String, Integer> keyValue = (k, v) -> 
    System.out.println(k + " = " + v);
keyValue.accept("Age", 25);

// Praktisches Beispiel: Map durchlaufen
Map<String, Integer> map = Map.of("a", 1, "b", 2);
map.forEach(keyValue); // forEach nimmt BiConsumer
```

### Spezialisierte Consumer
```java
IntConsumer intPrinter = i -> System.out.println("Int: " + i);
LongConsumer longPrinter = l -> System.out.println("Long: " + l);
DoubleConsumer doublePrinter = d -> System.out.println("Double: " + d);

intPrinter.accept(42);
```

## Supplier-Familie

### Supplier<T>
Liefert einen Wert, nimmt keine Parameter
```java
Supplier<String> greeting = () -> "Hello World";
System.out.println(greeting.get());

// Lazy Evaluation
Supplier<LocalDateTime> timestamp = LocalDateTime::now;
System.out.println(timestamp.get()); // Aktueller Zeitstempel

// Random Number Generator
Supplier<Integer> randomInt = () -> new Random().nextInt(100);
System.out.println(randomInt.get());
```

### Spezialisierte Supplier
```java
IntSupplier randomIntSupplier = () -> new Random().nextInt();
LongSupplier timestampSupplier = System::currentTimeMillis;
DoubleSupplier piSupplier = () -> Math.PI;
BooleanSupplier coinFlip = () -> new Random().nextBoolean();

System.out.println(timestampSupplier.getAsLong());
```

## Function-Familie

### Function<T,R>
Transformiert einen Wert in einen anderen
```java
Function<String, Integer> stringLength = s -> s.length();
Function<String, Integer> stringLength2 = String::length; // Method Reference

System.out.println(stringLength.apply("Hello")); // 5

// Verkettung
Function<String, String> removeSpaces = s -> s.replace(" ", "");
Function<String, String> toUpper = String::toUpperCase;
Function<String, String> cleanAndUpper = removeSpaces.andThen(toUpper);

System.out.println(cleanAndUpper.apply("hello world")); // HELLOWORLD

// Compose (umgekehrte Reihenfolge)
Function<String, String> upperThenClean = removeSpaces.compose(toUpper);
```

### BiFunction<T,U,R>
Nimmt zwei Parameter, gibt einen zurück
```java
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
BiFunction<Integer, Integer, Integer> max = Integer::max;

System.out.println(add.apply(5, 3)); // 8
System.out.println(max.apply(10, 20)); // 20
```

### UnaryOperator<T>
Spezialfall von Function<T,T> - gleicher Typ für Ein- und Ausgabe
```java
UnaryOperator<String> toUpper = String::toUpperCase;
UnaryOperator<Integer> square = x -> x * x;
UnaryOperator<String> addPrefix = s -> ">> " + s;

System.out.println(toUpper.apply("hello")); // HELLO
System.out.println(square.apply(5)); // 25
```

### BinaryOperator<T>
Spezialfall von BiFunction<T,T,T> - alle Typen gleich
```java
BinaryOperator<Integer> multiply = (a, b) -> a * b;
BinaryOperator<String> concat = (s1, s2) -> s1 + s2;
BinaryOperator<Integer> max = Integer::max;

System.out.println(multiply.apply(4, 6)); // 24

// Oft verwendet in Streams
List<Integer> numbers = List.of(1, 2, 3, 4, 5);
int sum = numbers.stream().reduce(0, Integer::sum); // BinaryOperator
```

### Spezialisierte Function-Interfaces
```java
// Primitive zu Primitive
IntUnaryOperator doubleInt = x -> x * 2;
IntBinaryOperator addInts = (a, b) -> a + b;

// Object zu Primitive
ToIntFunction<String> stringToLength = String::length;
ToDoubleFunction<String> stringToDouble = Double::parseDouble;

// Primitive zu Object
IntFunction<String> intToString = Integer::toString;
```

## Predicate-Familie

### Predicate<T>
Testet eine Bedingung, gibt boolean zurück
```java
Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isLong = s -> s.length() > 10;
Predicate<Integer> isEven = n -> n % 2 == 0;

System.out.println(isEmpty.test("")); // true
System.out.println(isEven.test(4)); // true

// Logische Verknüpfungen
Predicate<String> isNotEmpty = isEmpty.negate();
Predicate<String> isShortAndNotEmpty = isNotEmpty.and(s -> s.length() < 5);
Predicate<String> isEmptyOrLong = isEmpty.or(isLong);

System.out.println(isShortAndNotEmpty.test("Hi")); // true
```

### BiPredicate<T,U>
Testet Bedingung mit zwei Parametern
```java
BiPredicate<String, Integer> hasLength = (s, len) -> s.length() == len;
BiPredicate<Integer, Integer> isGreater = (a, b) -> a > b;

System.out.println(hasLength.test("Hello", 5)); // true
System.out.println(isGreater.test(10, 5)); // true
```

### Spezialisierte Predicates
```java
IntPredicate isPositive = x -> x > 0;
LongPredicate isLarge = x -> x > 1000000L;
DoublePredicate isNearZero = x -> Math.abs(x) < 0.001;

System.out.println(isPositive.test(-5)); // false
```

## Praktische Stream-Beispiele

### Mit verschiedenen Functional Interfaces
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

// Predicate für Filter
Predicate<String> startsWithA = s -> s.startsWith("A");
Predicate<String> isShort = s -> s.length() <= 3;

List<String> shortANames = names.stream()
    .filter(startsWithA.and(isShort))
    .collect(Collectors.toList());

// Function für Transformation
Function<String, String> toUpperCase = String::toUpperCase;
Function<String, Integer> nameLength = String::length;

List<Integer> nameLengths = names.stream()
    .map(nameLength)
    .collect(Collectors.toList());

// Consumer für forEach
Consumer<String> printer = name -> System.out.println("Name: " + name);
names.forEach(printer);

// Supplier für generate
Supplier<String> randomName = () -> "User" + new Random().nextInt(1000);
Stream.generate(randomName)
    .limit(3)
    .forEach(System.out::println);
```

### Komplexeres Beispiel
```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25, "Engineer"),
    new Person("Bob", 30, "Designer"),
    new Person("Charlie", 35, "Manager")
);

// Verschiedene Predicates
Predicate<Person> isAdult = p -> p.getAge() >= 18;
Predicate<Person> isEngineer = p -> "Engineer".equals(p.getJob());
Predicate<Person> isYoung = p -> p.getAge() < 30;

// Function für Transformation
Function<Person, String> getName = Person::getName;
Function<Person, String> getJobTitle = Person::getJob;
Function<Person, String> getDescription = p -> 
    p.getName() + " (" + p.getAge() + ", " + p.getJob() + ")";

// Komplexe Stream-Pipeline
List<String> descriptions = people.stream()
    .filter(isAdult.and(isYoung.or(isEngineer)))
    .map(getDescription)
    .collect(Collectors.toList());

descriptions.forEach(System.out::println);
```

## Method References Übersicht

| Art | Syntax | Lambda-Äquivalent | Beispiel |
|-----|--------|-------------------|----------|
| Statische Methode | `Class::method` | `x -> Class.method(x)` | `Integer::parseInt` |
| Instanz-Methode | `object::method` | `x -> object.method(x)` | `System.out::println` |
| Instanz-Methode (beliebiges Objekt) | `Class::method` | `x -> x.method()` | `String::length` |
| Konstruktor | `Class::new` | `x -> new Class(x)` | `ArrayList::new` |

## Eigene Functional Interfaces

```java
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
    
    // Default-Methoden sind erlaubt
    default TriFunction<T, U, V, R> andThen(Function<R, R> after) {
        return (t, u, v) -> after.apply(apply(t, u, v));
    }
}

// Verwendung
TriFunction<Integer, Integer, Integer, Integer> addThree = 
    (a, b, c) -> a + b + c;

System.out.println(addThree.apply(1, 2, 3)); // 6
```

## Best Practices

### 1. Method References bevorzugen
```java
// Weniger gut
Function<String, Integer> length1 = s -> s.length();
// Besser
Function<String, Integer> length2 = String::length;
```

### 2. Compose und andThen nutzen
```java
Function<String, String> process = 
    ((Function<String, String>) String::trim)
    .andThen(String::toLowerCase)
    .andThen(s -> s.replace(" ", "_"));
```

### 3. Predicates kombinieren
```java
Predicate<String> validation = 
    ((Predicate<String>) s -> !s.isEmpty())
    .and(s -> s.length() > 2)
    .and(s -> s.matches("[a-zA-Z]+"));
```

### 4. Wiederverwendbare Functions
```java
public class StringUtils {
    public static final Function<String, String> TRIM_LOWER = 
        s -> s.trim().toLowerCase();
    
    public static final Predicate<String> IS_EMAIL = 
        s -> s.contains("@") && s.contains(".");
}
```

## Häufige Anwendungsfälle

### Optional mit Functional Interfaces
```java
Optional<String> optional = Optional.of("Hello");

// Consumer für ifPresent
optional.ifPresent(System.out::println);

// Function für map
Optional<Integer> length = optional.map(String::length);

// Supplier für orElseGet
String result = optional.orElseGet(() -> "Default Value");

// Predicate für filter
Optional<String> filtered = optional.filter(s -> s.startsWith("H"));
```

### CompletableFuture
```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> "Hello") // Supplier
    .thenApply(String::toUpperCase) // Function
    .thenApply(s -> s + " World") // Function
    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "!")) // Function
    .whenComplete((result, ex) -> { // BiConsumer
        if (ex == null) {
            System.out.println("Result: " + result);
        }
    });
```