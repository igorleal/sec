
## Technologies
* Java
* Maven
* H2 Database

## Installation

### Linux
* Install maven: `apt-get install maven`

### Mac
* Download and Install [Maven](https://maven.apache.org/download.cgi)

### Windows
* Download and Install [Maven](https://maven.apache.org/download.cgi)


## Initialization

Clone the repository:
```sh
git clone https://github.com/igorleal/sec.git
```

Go to project folder:
```sh
cd sec/api
```

## Testing

```sh
mvn test
```

* All unit tests will be executed
* The results will be available inside the terminal

## Running

```sh
mvn spring-boot:run
```

* All APIs are available on http://localhost:8080
* Run the [https://github.com/igorleal/sec/tree/master/app]client to use all APIs