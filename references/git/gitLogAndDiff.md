# 📘 Git Log & Diff – Praktische Befehle

## 🔁 Letzte Commits anzeigen
| Befehl                              | Beschreibung                                   |
|-------------------------------------|------------------------------------------------|
| `git log`                           | Vollständiges Log mit Commit-Infos            |
| `git log -n 3`                      | Zeige die letzten 3 Commits                   |
| `git log --oneline`                | Kompakte Ein-Zeilen-Übersicht aller Commits  |
| `git log --oneline -n 5`            | Die letzten 5 Commits kompakt                 |
| `git log --graph --oneline --all`   | Commit-Verlauf als ASCII-Graph               |
| `git log --since="2 weeks ago"`     | Nur Commits der letzten 2 Wochen             |
| `git log --author="micha"`          | Nur Commits von bestimmtem Autor             |
| `git log file.txt`                 | Nur Commits, die `file.txt` geändert haben   |

## 🔍 Commit-Details & Unterschiede anzeigen
| Befehl                                     | Beschreibung                                        |
|--------------------------------------------|-----------------------------------------------------|
| `git show <commit>`                        | Details und Diff für einen bestimmten Commit       |
| `git show HEAD`                            | Letzten Commit anzeigen                            |
| `git show HEAD~1`                          | Vorletzten Commit anzeigen                         |
| `git diff`                                 | Unterschiede zwischen Arbeitsverzeichnis und Index |
| `git diff --cached`                        | Unterschiede zwischen Index und letztem Commit     |
| `git diff HEAD`                            | Änderungen seit letztem Commit                     |
| `git diff HEAD~2 HEAD`                     | Unterschiede zwischen vorletztem und letztem Commit|
| `git diff <commit1> <commit2>`             | Unterschied zwischen zwei beliebigen Commits       |

## 🧩 Formatierung & Filterung
| Befehl                                                | Beschreibung                                  |
|--------------------------------------------------------|-----------------------------------------------|
| `git log --stat`                                      | Welche Dateien in welchem Commit geändert     |
| `git log --patch`                                     | Zeigt Diff jeder Änderung im Log              |
| `git log --pretty=format:"%h %s (%an)"`               | Benutzerdefiniertes Format                    |
| `git log --name-only`                                | Nur die Dateinamen, die pro Commit geändert wurden |
| `git log --name-status`                              | Zeigt Status (A/M/D) + Dateinamen             |

## 📎 Konkrete Anwendungsbeispiele
```bash
git log -n 3 --oneline
# letzte 3 Commits kompakt

git show HEAD~1
# Vorletzten Commit anzeigen (inkl. Diff)

git diff origin/main..HEAD
# Zeigt Unterschiede zwischen lokalem Stand und remote main

git log --since="3 days ago" --author="micha"
# Letzte Commits von Micha in den letzten 3 Tagen

git diff HEAD~1 HEAD
# Änderungen des letzten Commits (gegen den vorherigen)
