# 🔍 Checkstyle Code Smells Detection & Refactoring

## 📖 Overview

This project presents a code smell detection and refactoring study conducted on the Checkstyle open-source project as part of the Software Design Patterns course.

The Ptidej tool was used to identify design issues, and selected code smells were analyzed and refactored to improve software maintainability, readability, and code quality.

---

## 🎯 Objectives

- Detect code smells using Ptidej
- Analyze the most significant design issues
- Refactor selected code smells
- Improve maintainability through refactoring

---

## 🛠 Technologies

- Java
- Maven
- Checkstyle
- Ptidej
- IntelliJ IDEA
- Git

---

## 📊 Code Smells Detected

| Code Smell | Instances |
|------------|----------:|
| ParentClassProvidesProtectedDetection | 399 |
| MethodNoParameterDetection | 387 |
| NotAbstract | 383 |
| NoPolymorphism | 369 |
| TwoInheritanceDetection | 291 |
| ChildClassDetection | 107 |
| DataClass | 44 |
| LargeClassDetection | 22 |

---

## ♻ Refactoring

### Example 1 – FewMethodsDetection

The unnecessary `FinallyHandler` class was removed and its responsibilities were integrated into `BlockParentHandler`, reducing unnecessary abstraction and improving maintainability.

### Example 2 – ClassOneMethod

The `IndexHandler` class originally contained only one empty method. It was enhanced by adding meaningful functionality and a helper method, eliminating the code smell.

---

## 📂 Repository Structure

```text
report/
Design_Queens_Ass2_Code/
refactored-files/
```

---

## 📄 Report


The complete report is available in the `report` folder.

If GitHub cannot preview the PDF, download the file locally to view it.

The complete project report is available in:

`report/Checkstyle_Code_Smells_Refactoring_Report.pdf`


---

## 👩‍💻 Author

**Münevver Polat**

Software Design Patterns – Assignment 2

2026
