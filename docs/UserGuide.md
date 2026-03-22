---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

ScamBook User Guide
---

<!-- * Table of Contents -->
<page-nav-print />

## Introduction

### What is ScamBook?

ScamBook is a **desktop contact management app** optimised for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

### Who is ScamBook for?

ScamBook is designed for **Singapore-based investment and financial scam callers** managing a high volume of victim contacts daily. If you are a scammer who:
* is **tech-savvy** and prefer typing to clicking, and comfortable with
a command line interfaces (CLI);
* operates as a **solo caller** and need a private, single-user system to
manage your contacts;
* handles **hundreds to thousands of contacts** and need a system that can
scale to your needs;
* needs to manage and update contacts **on-the-go during live calls**;

ScamBook is the app for you!

<!-- TODO: Potentially include a table of problems solved, value
proposition, etc. -->

--------------------------------------------------------------------------------------------------------------------

<!-- Quickstart appropriate for target users and fit-for-purpose -->
## Quick start

<!-- Quickstart: Installation instructions -->
### Installation
1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed
   [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
   <!-- TODO: Add detailed checking/installation instructions for JDK -->

1. Download the latest `.jar` file from
   [here](https://github.com/AY2526S2-CS2103T-T16-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for
   your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in,
   and use the `java -jar addressbook.jar` command to run the
   application.<br>

<!-- Quickstart: Overview of UI -->
### Overview
A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

![Ui](images/Ui.png) <!-- TODO: annotated screenshot of the UI -->

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add John Doe --phone 98765432 --email johnd@example.com --tag address:John street, block 123, #01-01` : Adds a contact named `John Doe` to ScamBook.

   * `tag 2 --add income:100000 --edit address:Tom street` : Adds the tag `income:100000` to the 2nd contact shown in the current list, and edits the `address` tag of that contact to be `Tom street`.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

<!-- Quickstart: Adding, editing, deleting, basic general workflow -->

### Basic commands

Refer to the [Features](#features) section below for details of each command, or the [Commands Summary](#commands-summary) section for a quick summary of all commands and their formats.

--------------------------------------------------------------------------------------------------------------------

## Features

Upon successful execution of a command, a corresponding success message will be displayed.

The `filter` command will only display a subset of filtered profiles in ScamBook. In general, after successful execution of any command, if the modified profile(s) still fulfill the most recent filter applied, the displayed list will remain as the filtered list. Otherwise, the displayed list will revert to show all profiles.

<!-- Disclaimer for command format, applicable to all commands -->
<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user. They can contain spaces and special characters (except `index`, which expects a single positive integer). <br>
  e.g. in `add NAME`, `NAME` is a parameter which can be used as `add John Doe`.

* Items in `[square brackets]` are optional.<br>
  e.g `NAME [--phone PHONE]` can be used as `John Doe --phone 88463679` or as `John Doe`.

* Items with `…`​ after them can be used multiple times (including zero times).<br>
  e.g. `[--tag NAME:VALUE]…​` can be used as ` ` (i.e. 0 times), `--tag school:NUS`, `--tag school:NUS --tag salary:10000` etc.

* Mandatory parameters must come before optional parameters.<br>
  e.g. if the command specifies `NAME [--phone PHONE]`, `--phone 88091246 John` is not acceptable.

* Optional parameters can be in any order.<br>
  e.g. if the command specifies `[--phone PHONE] [--email EMAIL]`, `--email john@example.com --phone 91842739` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br> e.g. if the command input is `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

<!--
Command User guide format:

### Command description (within 5 words) : `command`
Short description of the command.

Format: `command [parameters]`


[Following sections are optional, include only if applicable (try to be minimal)]

<box type="warning" seamless>
Warning about the command, e.g. common mistakes, important notes, etc.
</box>


Expected output or behaviour if the command is executed successfully. Use
screenshots (properly cropped) only if necessary, e.g. if the output is too
long or contains formatting that is hard to represent in text.


Examples: [DO NOT include unrealistic examples (use realistic params.) and DO
NOT include unlikely user input (e.g., names with backslaches) if already
handled by the app.]
* `Expected input`
  Explanation of output or behaviour if needed


<box type="tip" seamless>
Tips about the command, e.g. how to use it more effectively, etc.
</box>

-->

### Viewing help : `help`

Shows a pop-up window explaining how to use the basic commands. For more details on how to use this
application, you can also click on **Copy URL** to access the user guide.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add NAME [--phone PHONE] [--email EMAIL] [--tag TAGNAME:TAGVALUE]...`

<box type="tip" seamless>
**Tip:** A person can have any number of tags (including 0)

</box>

Examples:
* `add John Doe --phone 98765432 --email johnd@example.com --tag address:John street, block 123, #01-01`
* `add Besty Croew --tag income:$100000 --tag bank:OCBC`


### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`


### Filtering the list of persons : `filter`

<!-- TODO: filter description here-->


### Sorting the list of persons : `sort`

<!-- TODO: sort description here-->


### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [--name NAME] [--phone PHONE] [--email EMAIL]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be overwritten by the input values.

Examples:
* `edit 1 --phone 91234567 --email johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 --name Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.


### Tagging a person : `tag`

<!-- TODO: add visuals -->

Modifies (add, edit or delete) the tags of an existing person in the ScamBook.

Format: `tag INDEX [--add NAME:VALUE]... [--edit NAME:VALUE]... [--delete TAGNAME]...​`

<box type="warning" seamless>
**Caution:** `NAME`, `VALUE`, `TAGNAME` must NOT contain colons (`:`). Otherwise, an error will be displayed.
</box>

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided, if not, nothing will happen upon execution (and success message will be displayed).
* Optional fields beginning with `--add` represents tags to be added to the person. The tag name must NOT already exist.
* Optional fields beginning with `--edit` represents tags to be modified of the person. The tag with the corresponding name must already exist.
* Optional fields beginning with `--delete` represents tags to be deleted. The tag with the corresponding name must already exist.

<box type="warning" seamless>
If the same tag name appears across multiple optional fields, behavior is undefined.
</box>

Examples:
* `tag 10 --add school:National University of Sinapore` Adds a tag with name `school` and value `National University of Sinapore` to the tenth person. Note the support of spaces in the tag value.
* `tag 2 --delete age --edit monthly income:10000` Deletes an existing tag with name `age` and edits an existing tag with name `monthly income` to contain `10000` from the second person. Note the support of spaces in tag name and the flexible ordering of parameters. 
* `tag 1 --add school:NUS --edit salary:10000 --delete age` Adds a tag with name `school` and value `NUS`, edits an existing tag with name `salary` to contain `10000` and deletes an existing tag with name `age` from the first person.


### Marking the status of a person: `clearstatus`, `target`, `scammed`, or `ignore`

Sets the status of a specific person. We currently support 4 common statuses, each represented by its corresponding command name.

In this section, `status_command` can be replaced by either one of `clearstatus`, `target`, `scammed`, or `ignore`.

Format: `status_command INDEX`

* Sets the status of the person at the specified `INDEX`.
* The new status overwrites any previously existing status, i.e. each person can have exactly 1 status at any time (no status is also a status).
* Setting a particular status for a person that already has the corresponding status will do nothing (and success message will be displayed).
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `scammed 2` marks the second person to have status "scammed".
* `ignore 4` marks the fourth person to be ignored (e.g. if you think the fourth person is unlikely to be a victim and you should not pursue this further).
* `target 3` marks the third person as a potential target.
* `clearstatus 1` clears the first person of any indicated status.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`


### Deleting the app and all data: `nuke`

<!-- TODO: nuke description here-->


### Exiting the program : `exit`

Exits the program.

Format: `exit`


### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>
**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

<!-- Upcoming features -->



--------------------------------------------------------------------------------------------------------------------

## Tutorials

<!-- Tutorial: Working with tags, general workflow -->

<!-- Tutorial: Data transfer

How do I transfer my data to another Computer?<br>

Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.
-->

<!-- Tutorial: Power user features, shortcuts, efficient usage (only if features implemented) -->

## FAQ
**Q**: I have a question that is not answered here. Where can I ask it?<br>
**A**: You can ask your question by creating a new issue in the [ScamBook
repository](https://github.com/AY2526S2-CS2103T-T16-1/tp/issues).

<!-- Known issues, e.g. bugs, limitations, etc. Only add if affects a normal user experience. Ideally this section does not exist.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

-->

--------------------------------------------------------------------------------------------------------------------

## Commands summary
<!-- A summary of all commands. Should be of same/similar format as help
command output -->

Command   | Parameters
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**`add`**    | `NAME [--phone PHONE] [--email EMAIL] [--tag NAME:VALUE]...`<br> e.g., `add John Doe --phone 98765432 --email jognd@example.com --tag school:NUS`
**`tag`**    | `INDEX [--add NAME:VALUE]... [--edit NAME:VALUE]... [--delete TAGNAME]...`<br> e.g., `tag 1 --add school:NUS --edit salary:10000 --delete age`
**`edit`**   | `INDEX [--name NAME] [--phone PHONE] [--email EMAIL]`<br> e.g., `edit 1 --name Jane Doe --phone 91234567 --email newemail@example.com`
**`filter`** | `[--name NAME]... [--phone PHONE]`<br> e.g., `filter --name John --phone 98765432`
**`list`**   | List all contacts
**`delete`** | `INDEX`<br> e.g., `delete 1`
**`clear`**  | Delete all contacts
**`nuke`**   | Delete this app and all locally stored data
**`help`**   | Show this help message
**`exit`**   | Exit the application
