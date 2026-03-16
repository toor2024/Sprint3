# Sprint 1 Report: Solitaire Requirements

## I. User Stories
User Story Template: `As a <role>, I want <goal> [so that <benefit>]`

| ID | User Story Name | User Story Description | Priority | Estimated effort (hours) | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | Choose a board size | As a player, I want to choose the board size before starting a game so that I can play at my preferred complexity level. | High | 2 | LLM-assisted |
| 2 | Choose the board type | As a player, I want to choose the board type (English, Hexagon, or Diamond) so that I can play on my preferred layout. | High | 2 | LLM-assisted |
| 3 | Start a new game of the chosen board size and type | As a player, I want to start a new game using my selected board size and board type so that the board is initialized correctly for play. | High | 3 | Manual |
| 4 | Make a move in a game | As a player, I want to make a valid move by jumping one peg over another into an empty slot so that I can progress toward solving the puzzle. | High | 5 | Manual |
| 5 | A game is over | As a player, I want the game to detect when no more valid moves exist or when one peg remains so that I know whether I have won or lost. | High | 3 | Manual |

## II. Acceptance Criteria
Status values used: `completed`, `toDo`, `inProgress`

| User Story ID and Name | AC ID | Scenario Description | Given | When | Then | Status |
| --- | --- | --- | --- | --- | --- | --- |
| 1. Choose a board sizex | 1.1 | Select a valid board size before game start | Given the game setup screen is visible and no game is active | When the player selects board size `7` | Then board size selection is stored and displayed as the active setup value | toDo |
| 1. Choose a board size | 1.2 | Reject invalid board size input | Given the game setup screen is visible | When the player enters an invalid board size (non-numeric or out of allowed range) | Then the game rejects the input and keeps the previous valid size selection | toDo |
| 2. Choose the board type | 2.1 | Select an available board type | Given the game setup screen is visible and board type options are shown | When the player selects `English` board type | Then the selected board type is stored as the active setup value | toDo |
| 2. Choose the board type | 2.2 | Change board type before starting | Given the player previously selected one board type | When the player changes the selection to another available type | Then the latest selected board type becomes the active setup value | toDo |
| 3. Start a new game of the chosen board size and type | 3.1 | Start new game with selected setup | Given a valid board size and board type are selected | When the player clicks `New Game` | Then a new board is generated using the selected size and type | toDo |
| 3. Start a new game of the chosen board size and type | 3.2 | Reset board state on new game | Given a game is already in progress | When the player clicks `New Game` again | Then current board state is cleared and replaced with a fresh initial board for the selected setup | toDo |
| 4. Make a move in a game | 4.1 | Execute a valid move | Given a board state where at least one legal jump exists | When the player selects a legal source peg and destination hole | Then the move is applied, the jumped peg is removed, and the board is updated | toDo |
| 4. Make a move in a game | 4.2 | Prevent an invalid move | Given a board state where a selected move is illegal | When the player attempts the illegal move | Then the move is rejected and the board state remains unchanged | toDo |
| 5. A game is over | 5.1 | Detect a solved game | Given gameplay has progressed to one remaining peg | When the game evaluates end-state conditions after a move | Then the game reports a win state | toDo |
| 5. A game is over | 5.2 | Detect no-move remaining game-over | Given gameplay has progressed to a state with no legal moves and more than one peg | When the game evaluates end-state conditions after a move | Then the game reports a game-over (loss) state | toDo |

## III. LLM Evidence (Required)
Two user stories and two acceptance criteria used LLM support. Screenshots and corrections are below.

### Screenshot Files
| Item | File |
| --- | --- |
| LLM prompt/response screenshot for User Story 1 | `Sprint1Evidence/us1_prompt_response.png` |
| LLM prompt/response screenshot for User Story 2 | `Sprint1Evidence/us2_prompt_response.png` |
| LLM prompt/response screenshot for AC 1.1 | `Sprint1Evidence/ac11_prompt_response.png` |
| LLM prompt/response screenshot for AC 2.1 | `Sprint1Evidence/ac21_prompt_response.png` |

### Prompt Texts Used

#### Prompt for User Story 1
```text
Create a user story using this template: "As a <role>, I want <goal> so that <benefit>".
Context: Solitaire game. Feature: choosing board size before starting a new game.
Keep it 1 sentence. Do not add acceptance criteria.
```

#### Prompt for User Story 2
```text
Create a user story using this template: "As a <role>, I want <goal> so that <benefit>".
Context: Solitaire game. Feature: choosing board type (English, Hexagon, Diamond) before starting a new game.
Keep it 1 sentence. Do not add acceptance criteria.
```

#### Prompt for AC 1.1 (Given/When/Then)
```text
Write acceptance criteria in Given/When/Then format for this user story:
"As a player, I want to choose the board size before starting a game so that I can play at my preferred complexity level."
Scenario: select a valid board size before game start.
Output exactly one Given/When/Then.
```

#### Prompt for AC 2.1 (Given/When/Then)
```text
Write acceptance criteria in Given/When/Then format for this user story:
"As a player, I want to choose the board type (English, Hexagon, or Diamond) so that I can play on my preferred layout."
Scenario: select an available board type.
Output exactly one Given/When/Then.
```

### Errors Corrected from LLM Output
- User Story 1 correction note: The LLM version did not mention when the selection happens and implied size could change mid-game. I corrected it to specify before starting a game and kept it to one sentence: "As a player, I want to choose the board size before starting a game so that I can play at my preferred complexity level."
- User Story 2 correction note: The LLM version listed board types but did not include the benefit ("so that..."). I added the benefit and kept the exact allowed types: "As a player, I want to choose the board type (English, Hexagon, or Diamond) so that I can play on my preferred layout."
- AC 1.1 correction note: The LLM output was vague ("size is set") and did not include a clear observable result. I revised the Then clause to require the chosen size be stored and displayed as the active setup value.
- AC 2.1 correction note: The LLM output mixed multiple scenarios (selecting and starting a game). I narrowed it to only selecting a type and confirming it becomes the active setup value.
