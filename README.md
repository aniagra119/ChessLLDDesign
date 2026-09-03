# Chess Game — Low-Level Design (LLD)

[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge&logo=apachemaven&logoColor=white)](pom.xml)
[![Design Patterns](https://img.shields.io/badge/Design_Patterns-GoF_%26_SOLID-blueviolet?style=for-the-badge)](ChessDesign.md)

A production-grade, object-oriented Low-Level Design for a standard two-player Chess Game implemented in Java 21 adhering to clean architecture, SOLID principles, and GoF design patterns.

---

## 1. System Requirements & Feature Matrix

| # | Requirement | Implementation Details | Design Pattern / Architecture |
| :---: | :--- | :--- | :--- |
| **1** | **8x8 Chessboard Representation** | Canonical 64 squares with algebraic notation (`A1` to `H8`) and bounds safety. | `Board`, `Position` (Immutable Value Object / Flyweight) |
| **2** | **Full Piece Roster** | King, Queen, Rook, Bishop, Knight, and Pawn with color differentiation. | `Piece` hierarchy, `PieceColor` enum |
| **3** | **Special Chess Rules** | En Passant, Castling (Kingside & Queenside), and Pawn Promotion to Queen/Rook/Bishop/Knight. | `CastlingMove`, `PromotionMove`, `EnPassantMove` |
| **4** | **Two-Phase Move Validation** | Geometric trajectory evaluation cleanly separated from King-safety / pin checks. | `Piece.getPseudoLegalMoves()` + `MoveValidator` |
| **5** | **Move Execution & Rollback** | Encapsulated move commands with `execute()` and `undo()` for simulation & check validation. | `Move` hierarchy (Command Pattern) |
| **6** | **Game State Tracking** | Active state machine tracking `IN_PROGRESS`, `CHECK`, `CHECKMATE`, `STALEMATE`, and `DRAW`. | `GameStatus`, `GameStateEvaluator` |
| **7** | **Turn Management** | Two-player alternation enforcing valid turn order and capture history. | `Game`, `Player`, `TurnManager` |
| **8** | **Flexible Assembly** | Fluent setup for standard starting boards or custom endgame testing positions. | `GameBuilder` (Builder Pattern) |

---

## 2. High-Level Architecture

```mermaid
graph TD
    Client([Client / Test Suite]) -->|Interacts with| G[Game Facade]
    GB[GameBuilder] -->|Assembles| G
    
    subgraph Domain & Board Layer
        G --> B[Board]
        B --> Pos[Position - Value Object / Flyweight]
        B --> Piece[Piece Hierarchy]
        Piece --> King[King]
        Piece --> Queen[Queen]
        Piece --> Rook[Rook]
        Piece --> Bishop[Bishop]
        Piece --> Knight[Knight]
        Piece --> Pawn[Pawn]
    end
    
    subgraph Command & Action Layer
        G --> M[Move Command Hierarchy]
        M --> SM[StandardMove]
        M --> CM[CastlingMove]
        M --> PM[PromotionMove]
        M --> EPM[EnPassantMove]
        M -->|execute/undo| B
    end

    subgraph Players & Telemetry
        G --> P1[Player: White]
        G --> P2[Player: Black]
        G --> GS[GameStatus State Machine]
        G --> Hist[Move History]
    end
```

---

## 3. Detailed Specifications

For in-depth architectural rationale, SOLID breakdowns, and design pattern justifications, see [ChessDesign.md](ChessDesign.md).
