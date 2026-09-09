# Electric Guitar Finder

Electric Guitar Finder is a Java application created for COSC120 Assignment 3.

The application allows a user to search for an electric guitar based on their preferred characteristics. Matching guitars are loaded from a text file and displayed using a Java Swing `JOptionPane` interface.

## Features

The user can search using:

- Guitar type
- Brand
- Pickup type
- Number of strings
- Handedness
- Active pickups
- Music genres
- Minimum price
- Maximum price

Optional search criteria include an **"I don't mind"** option so that the user can skip filters they do not care about.

The application can:

- Load guitar information from `guitars.txt`
- Search and return multiple matching guitars
- Handle multiple music genres using a `Set`
- Validate price input
- Validate customer name, phone number and email address
- Display detailed guitar information
- Save a customer's guitar enquiry to a text file
- Handle invalid input and file errors
- Display a custom guitar icon in the user interface

## Project Structure

The main Java classes include:

- `GuitarFinder` - runs the application and handles user interaction
- `Guitar` - represents an individual guitar
- `DreamGuitar` - stores searchable guitar characteristics and customer preferences
- `GuitarRegistry` - stores guitars and performs searches
- `Customer` - stores customer enquiry information
- `Filter` - identifies searchable characteristics

The project also contains enums for:

- `GuitarType`
- `PickupType`
- `Handedness`
- `Genre`

## Data Types and Collections

The project uses several Java data types and collections, including:

- `String`
- `int`
- `float`
- `double`
- `boolean`
- enums
- `Set`
- `List`
- `Map`

Searchable guitar characteristics are stored using:

```java
Map<Filter, Object>