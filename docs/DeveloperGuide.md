---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# ScamBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Below is a class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

When `Logic` is called to execute a command, the following happens:

1. The command is passed to an `AddressBookParser` object. It checks the first word of the command (known as the **command word**), and based on that, creates a parser that matches the command (e.g., DeleteCommandParser) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

#### Parsing & Input Patterns

The following is a (partial) class diagram for the parser

<puml src="diagrams/ParserClasses.puml" width="600"/>

Each `Parser` contains an `InputPattern`, which is a specification for the pattern of arguments & parameters this command accepts.

An `InputPattern` consists of a list of `Token` and a list of `Param`.

The list of `Token` represents the compulsory arguments that come after the command word. The list of tokens is ordered.
Different Tokens accept different inputs, such as `String`s, `Integer`s, etc.

The list of `Param` represents optional arguments that come after the compulsory argument. 
These are specified by a param id starting with `--`. These can be provided in any order.


For example, the `add` command has the following format `add NAME [--phone PHONE] [--email EMAIL] [--tag NAME:VALUE]...`. 
The command has one `Token`: which takes in the name. It also has 3 `Param`, which represent the phone, email and tag respectively.

Note that `Param` has the functionality to specify how many times it can appear in a valid input. 
In this case, `--phone` and `--email` can appear between 0 and 1 times, while `--tag` can appear between 0 and 100 times.
### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Input Pattern Parsing

Once the command is received, we denote `args` as the string that comes after the command word.

Subsequently, we find the first occurrence of a `Param` id. For example, in the `add` command,
we find the first occurrence of `--phone`, `--email` or `--tag` as these are the 3 `Param` it accepts.

If nothing is found, then it is assumed that the entire input contains zero params. From the first occurrence,
we split the `args` into `tokenArgs` and `paramArgs`. These are then parsed separately.

For `tokenArgs`, we split it into a number of segments by spaces.
Each `Token` has a function `allowSpaces` which specifies if it allows spaces or not. 
For each token in order, if it does not allow spaces, we assign it to the next segment. 
If it allows spaces, we find the first segment that matches the next token, and then assign segments in between to this token.

If during the assigning there are too many or too few tokens, or if any segment does not match
the requirements of a token, a `ParseException` is thrown.

Note: tokens that allow spaces can lead to ambiguous parsing. It is advised to keep such tokens as the very last token in general.
In particular, avoid two tokens that allow spaces beside each other.

For the `paramArgs`, they are split by the string `<space>--` and then assigned one by one.
If a `--` does not match the id of any Params, a `ParseException` is thrown.

### Sort feature

The sort mechanism allows users to sort the person list by various fields (name, phone, email, or tag values) in ascending or descending order, with support for numeric or alphabetic comparison modes.
Note that the `sort` command does NOT use the input pattern methods as mentioned above, since its parameter values are different.

#### Implementation

The sort feature is implemented through the following key classes:
* `SortCommandParser` — Parses user input and creates a `SortSpec` containing the sort parameters
* `SortCommand` — Builds a `Comparator<Person>` and applies it to the model
* `SortSpec` — Value object encapsulating sort parameters (target field, order, mode)

The following sequence diagram shows how a sort operation is executed:

<puml src="diagrams/SortSequenceDiagram.puml" alt="SortSequenceDiagram" />

How the sort command works:

1. The user issues a sort command (e.g., `sort phone --desc`).
2. `SortCommandParser` parses the arguments and creates a `SortSpec` with the target field (`PHONE`), order (`DESC`), and mode (`NUMBER` by default).
3. `SortCommand` is created with the `SortSpec` and executed.
4. During execution, `SortCommand` builds a `Comparator<Person>` based on the `SortSpec`.
5. The comparator is applied to the view via `Model#updateSortedPersonList()`.
6. The `list` command resets both the filter and the sort order via `Model#resetSortedPersonList()`, restoring the original insertion order.

#### Design considerations

**Aspect: How sort handles null/missing values:**

* **Current choice:** Nulls always sort last, regardless of ascending/descending order.
    * Pros: Predictable behavior; missing data doesn't clutter results.
    * Cons: Less flexible for users who want nulls first.

**Aspect: View-only sorting:**

* **Current choice:** Sort only affects the view layer (`SortedList`), not the underlying data.
    * Pros: Original insertion order is preserved and can be restored with `list` command.
    * Cons: Sort order does not persist across sessions.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Phone-call based scam caller who
* is based in Singapore
* executes financial scams
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* Manages large volumes of scam caller victims' contact information in Singapore
* Possible identification, and visualisation of victim social networks
* Filter/sort for high-risk / low-reward victims
* Reminders to follow-up on certain higher potential victims
* Single-user application for security and anonymity


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                        | I want to …​                                                                                 | So that I can…​                                                                  |
|----------|--------------------------------|----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `* * *`  | scam caller                    | create potential victim profile with attached information                                    | I know who to scam next                                                          |
| `* * *`  | scam caller                    | delete potential victim profile                                                              | I know who not to scam next or who I have scammed                                |
| `* * *`  | scam caller                    | list potential victim profiles                                                               | I can see all potential victims                                                  |
| `* * *`  | scam caller                    | quickly append new information about someone I'm calling                                     | I can use that information in the future against that person                     |
| `* * *`  | scam caller                    | quickly search up personal information about someone I'm calling                             | I can use that information to gain their credibility                             |
| `* * *`  | sometimes-offline scam caller  | access the address book offline                                                              | I can work reliably on the go                                                    |
| `* * *`  | organised scam caller          | filter and sort contacts by attributes                                                       | I can focus on the best next calls                                               |
| `* * *`  | expert user                    | specify optional parameters with command flags                                               | I have more flexibility when using commands                                      |
| `* *`    | new user                       | view a help menu                                                                             | I understand what I can do with the product                                      |
| `* *`    | scam caller                    | mark a victim is attempted to call but not tricked                                           | I can avoid calling that person again                                            |
| `* *`    | successful scam caller         | create custom fields                                                                         | personalise relevant details for each victim for greater success                 |
| `* *`    | scam caller                    | edit potential victim profile                                                                | I can update victim profile as more information gets known                       |
| `* *`    | beginner user                  | get help on the commands                                                                     | I can familiarise myself with the various tools at my disposal                   |
| `* *`    | scam caller                    | filter by high risk demographics such as old age & high reward demographics like high income | prioritize who I should call                                                     |
| `* *`    | scam caller                    | draw and label a relationship from one person to another person                              | when I scam someone, I can use personal information about another person as bait |
| `* *`    | successful scam caller         | import and manage a large contact list                                                       | I can work with a larger number of victims                                       |
| `* *`    | busy scam caller               | see and quickly type auto-filled commands                                                    | I can avoid typing repetitive commands and quickly add new victims               |
| `* *`    | beginner scam caller           | load and interact with sample data                                                           | I have the freedom to try commands without having access to a large victim base  |
| `* *`    | scam caller                    | set reminders to follow up calls on victims                                                  | I can review which targets need to be called again                               |
| `* *`    | scam caller                    | view a detailed dashboard of a specific victim                                               | refer to that victim's information during a scam call                            |
| `* *`    | high-volume scam caller        | obtain search results quickly even with large numbers of contacts                            | I am not slowed down during operations                                           |
| `* *`    | security-conscious scam caller | have encrypted local storage and auto-lock                                                   | sensitive data is protected and secure                                           |
| `* *`    | security-conscious scam caller | require logging in for the app                                                               | sensitive data is protected and secure                                           |
| `*`      | scam caller                    | purge data immediately                                                                       | I can wipe my hard disk if I get raided by the police                            |
| `*`      | new user                       | follow a tutorial                                                                            | I am guided through the onboarding process                                       |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Create potential victim profile**

**MSS:**

1. User requests to create a new potential victim profile with name and other attributes.
2. System validates the name and attributes.
3. System saves the new profile and displays a success message with the created profile summary.

    Use case ends.

**Extensions:**

* 1a. Victim name is unspecified or invalid.
    * 1a1. System shows an error message indicating the issue with the name.

      Use case ends.

* 2a. Specified attribute(s) is/are invalid.
    * 2a1. System shows an error message with the expected format.

      Use case ends.


**Use case: UC02 - Delete potential victim profile**

**MSS:**

1. User requests to delete a potential victim profile.
2. System deletes the specified profile and displays a success message with the deleted profile's name.

    Use case ends.

**Extensions:**

* 1a. Request format is invalid or victim profile is unspecified.
    * 1a1. System shows an error indicating the expected specification format.

      Use case ends.

* 1b. Specified victim does not exist.
    * 1b1. System shows an error indicating non-existent victim and potential remedies.

      Use case ends.


**Use case: UC03 - Search up victim profile**

**MSS:**

1. User requests to search for victim profiles by name or other attributes.
2. System searches stored profiles for matches.
3. System displays a list of all matching profiles with their stored details.
 
    Use case ends.

**Extensions:**

* 1a. Request format is invalid.
    * 1a1. System shows an error indicating the expected command format.

      Use case ends.

* 2a. No profiles match the query.
    * 2a1. System shows a message indicating no results found.

      Use case ends.


**Use case: UC04 - Sort contacts by tag(s)**

**MSS:**

1. User requests to sort profiles by specified tag(s).
2. System validates the specified tag(s).
3. System displays the sorted list of profiles.

    Use case ends.

**Extensions:**

* 2a. No profile has the specified tag, or the tag name is invalid.
    * 2a1. System shows an error listing example valid tags.

      Use case ends.

    
**Use case: UC05 - Append New Information to a Victim Profile**

**MSS:**

1. User requests to append information to a specified profile.
2. System validates the specification and the new information.
3. System updates the profile with the new information and displays a success message with the updated profile.

    Use case ends.

**Extensions:**

* 2a. No new information is provided or profile is not well specified.
    * 2a1. System shows an error indicating the expected specification or information format.

      Use case ends.
 
* 2b. The new information is invalid.
    * 2b1. System shows an error indicating the expected information format.

      Use case ends.

* 2c. The new information conflicts with existing information (e.g., trying to append a tag that already exists).
    * 2c1. System shows an error indicating the conflict.

      Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 victims without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be fully functional without an internet connection.
5. Should accept only ASCII characters in user input, and display all stored information in ASCII characters.

### Glossary

* **Victim Profile**: A contact entry representing a potential scam target, containing name, phone, email and custom tags
* **Tag**: A key-value pair of information attached to a victim profile, e.g., `job : investment banker`, `yearly income : $100000`
* **Mainstream OS**: Windows, Linux, Unix, MacOS

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
