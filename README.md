### What is this repository for?

* This is a simple web-app written in spring-boot to mimic the 8tracks playlist APIs 

### How do I get set up?
1. Change the application.properties to point to your Database
2. Make sure you have maven installed on your system
3. Run the command *mvn clean install -DskipTests
4. The app should startup and would be by default hosted on localhost:8080

### Defaults
- The default database name is "assignment"
- The default user name and pass for mysql is root/root


### Endpoints

- playlist

	- GET playlist/ (paginated)
	  Lists all the available playlists 
	  
	- GET playlist/<playlist_id>
	  Returns a specific playlist if found 
	  
	- PUT playlist/<playlist_id> {Request Body as playlist}
	  Updates a given playlist and returns the updated object
	  
	- DELETE playlist/<playlist_id>
	  Deletes a given playlist. Returns an empty response if successful
	  
	- GET playlist/explore/<tag1,tag2,tag3> (paginated)
	  Returns a list of playlists with a list of tags (for further filtering) based on the provided comma-separated list of tags
	  
	  
- tag
	- GET tag/ (paginated)
	  Lists all the available tags
	- GET playlist/<tag_name>
	  Returns a specific tag if found
	  
- health
	- GET health/
	  Returns the status of the service
	  

### Schemas

- Playlist 
```
	{
            "id": Long,
            "createdAt": UNIX_TIMESTAMP,
            "updatedAt": UNIX_TIMESTAMP,
            "name": "String",
            "authorName": "String",
            "tags": [
                {
                    "name": "String"
                },
                {
                    "name": "String"
                }
            ],
            "likeCount": Integer,
            "playCount": Integer
     }
```
	 
- Tag
```
	{
		"name" : "String"
	}
```
	
### Standards

1. All paginated Responses are of the following example format:
```
	{
    	"content": [ objects ],
		"totalPages": 1,
	    "totalElements": 4,
    	"last": true,
	    "size": 20,
    	"number": 0,
	    "sort": null,
    	"first": true,
	    "numberOfElements": 4
	}
```

2. The *playlist/explore* endpoint's response is of the following format:
```
	{
		"playlists": {
			"content": [list_of_playlists],
			"last": true,
			"totalElements": 1,
			"totalPages": 1,
			"size": 20,
			"number": 0,
			"sort": null,
			"first": true,
			"numberOfElements": 1
		},
		"tags": [list_of_tags]
	}
```

### Contact
For any further clarifications, please feel free to contact: manish001992@gmail.com
