## Find if a path exists between two cities
#### This application determines if two cities are connected. 

Two points are considered connected if there’s a series of roads that can be traveled from one city to another.

Demo list of roads is available in a project file `cities.txt` located in the `resource` directory. 
File contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.

In the example below 
* city `a` has exactly 2 roads connecting cities `b` and `d`. 
* cities `e` or `f` are not reachable from any of `a`, `b`, `c` or `d`
* `a` is connected to `c` via `d`
*  `f` is connected to `k` via `e` via `l`
```
a,b  
a,d
b,c
d,a
e,f
a,a
k,l
l,e    
```

### Build from source
```bash
    mvn clean install
```
### Run the application

Using maven Spring Boot plugin 
``` 
    mvn spring-boot:run 
```
Using Java command line 
```
    java -jar target/transit-0.0.1.jar
```

### Show the city list and the satellite cities 

[http://localhost:4242/](http://localhost:4242/) 


### Play with it

Example `a` and `e` _are not_ connected:

[http://localhost:4242/connected?origin=a&destination=e](http://localhost:4242/connected?origin=a&destination=e) (result **false**)

Example `f` and `k` _are_ connected:

[http://localhost:4242/connected?origin=f&destination=k](http://localhost:4242/connected?origin=f&destination=k) (result **true**)

 
Example `d` and `k` _are not_ connected

[http://localhost:4242/connected?origin=d&destination=k](http://localhost:4242/connected?origin=a&destination=e) (result **false**)

### Provide your own roadmap file

Using maven Spring Boot plugin 
``` 
    mvn spring-boot:run -Ddata.file=/tmp/mytest.txt 
```
Using Java command line 
```
    java -Ddata.file=/tmp/mytest.txt -jar target/transit-0.0.1.jar
   
```
### Swagger

[http://localhost:4242/swagger-ui.html](http://localhost:4242/swagger-ui.html)
   

