# Java Path Methoden - Cheat Sheet

## Grundlagen
```java
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
```

## Path erstellen

| Methode | Beschreibung | Beispiel |
|---------|--------------|----------|
| `Paths.get(String)` | Path aus String erstellen | `Path p = Paths.get("/home/user/file.txt");` |
| `Paths.get(String, String...)` | Path aus mehreren Komponenten | `Path p = Paths.get("/home", "user", "file.txt");` |
| `Path.of(String)` | Moderne Alternative (Java 11+) | `Path p = Path.of("/home/user/file.txt");` |

## Path-Informationen abrufen

| Methode | Rückgabe | Beschreibung | Beispiel |
|---------|----------|--------------|----------|
| `getFileName()` | `Path` | Dateiname (letztes Element) | `file.txt` |
| `getParent()` | `Path` | Übergeordnetes Verzeichnis | `/home/user` |
| `getRoot()` | `Path` | Wurzelverzeichnis | `/` (Linux) oder `C:\` (Windows) |
| `toString()` | `String` | Path als String | `"/home/user/file.txt"` |
| `toAbsolutePath()` | `Path` | Absoluter Pfad | `/home/user/project/file.txt` |
| `normalize()` | `Path` | Bereinigter Pfad (entfernt `.` und `..`) | `/home/user/file.txt` |

## Path-Eigenschaften prüfen

| Methode | Rückgabe | Beschreibung |
|---------|----------|--------------|
| `isAbsolute()` | `boolean` | Ist absoluter Pfad? |
| `getNameCount()` | `int` | Anzahl der Pfadelemente |
| `getName(int)` | `Path` | Element an Position i |
| `startsWith(Path)` | `boolean` | Beginnt mit anderem Path? |
| `endsWith(Path)` | `boolean` | Endet mit anderem Path? |

## Path-Operationen

| Methode | Rückgabe | Beschreibung | Beispiel |
|---------|----------|--------------|----------|
| `resolve(Path)` | `Path` | Pfade zusammenfügen | `parent.resolve("child")` |
| `resolve(String)` | `Path` | String zu Path hinzufügen | `dir.resolve("file.txt")` |
| `resolveSibling(Path)` | `Path` | Geschwisterpfad erstellen | `file.resolveSibling("other.txt")` |
| `relativize(Path)` | `Path` | Relativen Pfad erstellen | `base.relativize(target)` |
| `subpath(int, int)` | `Path` | Teilpfad extrahieren | `p.subpath(1, 3)` |

## Dateisystem-Operationen (mit Files-Klasse)

| Methode | Beschreibung | Beispiel |
|---------|--------------|----------|
| `Files.exists(Path)` | Prüft Existenz | `Files.exists(path)` |
| `Files.isDirectory(Path)` | Ist Verzeichnis? | `Files.isDirectory(path)` |
| `Files.isRegularFile(Path)` | Ist reguläre Datei? | `Files.isRegularFile(path)` |
| `Files.isReadable(Path)` | Ist lesbar? | `Files.isReadable(path)` |
| `Files.isWritable(Path)` | Ist schreibbar? | `Files.isWritable(path)` |
| `Files.size(Path)` | Dateigröße in Bytes | `Files.size(path)` |

## Praktische Beispiele

### Path-Manipulation
```java
Path original = Paths.get("/home/user/documents/../pictures/photo.jpg");
Path normalized = original.normalize();  // /home/user/pictures/photo.jpg
Path parent = normalized.getParent();     // /home/user/pictures
Path filename = normalized.getFileName(); // photo.jpg
```

### Pfade zusammenfügen
```java
Path baseDir = Paths.get("/home/user");
Path configFile = baseDir.resolve("config").resolve("app.properties");
// Ergebnis: /home/user/config/app.properties
```

### Relative Pfade
```java
Path from = Paths.get("/home/user/project");
Path to = Paths.get("/home/user/documents/file.txt");
Path relative = from.relativize(to);  // ../documents/file.txt
```

### Pfad-Vergleiche
```java
Path path = Paths.get("/home/user/file.txt");
boolean startsWithHome = path.startsWith("/home");        // true
boolean endsWithTxt = path.endsWith("file.txt");          // true
boolean isAbsolute = path.isAbsolute();                   // true
```

### Pfadelemente durchlaufen
```java
Path path = Paths.get("/home/user/documents/file.txt");
System.out.println("Anzahl Elemente: " + path.getNameCount()); // 4

for (int i = 0; i < path.getNameCount(); i++) {
    System.out.println("Element " + i + ": " + path.getName(i));
}
// Element 0: home
// Element 1: user  
// Element 2: documents
// Element 3: file.txt
```

## Häufige Anwendungsfälle

### Konfigurationsdatei finden
```java
Path configDir = Paths.get(System.getProperty("user.home"), ".myapp");
Path configFile = configDir.resolve("config.properties");
if (Files.exists(configFile)) {
    // Konfiguration laden
}
```

### Backup-Datei erstellen
```java
Path originalFile = Paths.get("document.txt");
Path backupFile = originalFile.resolveSibling(
    originalFile.getFileName() + ".backup"
);
// document.txt.backup
```

### Dateierweiterung ändern
```java
Path file = Paths.get("document.txt");
String nameWithoutExt = file.getFileName().toString()
    .replaceFirst("\\.[^.]*$", "");
Path newFile = file.resolveSibling(nameWithoutExt + ".pdf");
// document.pdf
```

## Wichtige Hinweise

- **Immutable**: Path-Objekte sind unveränderlich
- **Plattformunabhängig**: Automatische Anpassung an Betriebssystem
- **Lazy**: Keine Dateisystem-Zugriffe bei Path-Operationen
- **Files-Klasse**: Für tatsächliche Dateisystem-Operationen verwenden
- **Exception-Handling**: Viele Files-Methoden werfen IOException