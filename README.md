# Forumapi

[API](https://forum-api-live.herokuapp.com/)

# Documentation
Welcome to version 1 of the Forum API. Below you will find a current list of the available methods. 

User

Topic

## User

**Create User** 

>POST /api/v1/people

- Body 

  ```JSON
  {
      "firstName": "User",
      "lastName": "New",
      "email": "novo@novo.com"
  }
  ```

- Response 

  - Status 201

    ```json
    {
        "message": "Person created with ID 1"
    }
    ```

  - Status 400

    ```json
    {
    	"status": 400,
        "error": "Bad Request",
        "message": " "
    }
    ```

    

**Find User**

> GET /api/v1/people

- 

  - Status 200

    ```json
    [
        {
            "id": 1,
            "firstName": "Novo",
            "lastName": "Novo",
            "email": "novo@novo.com"
        }
    ]
    ```

**Find User By Id**

> GET /api/v1/people/{id}

- Parameters  

  `id ---- integer ---- required  `

- Response 

  - Status 200

    ```json
    {
        "firstName": "User",
        "lastName": "New",
        "email": "novo@novo.com"
    }
    ```

  - Status 404

    ```json
    {
    	"status": 404,
        "error": "Not Found",
        "message": "Person not found with id 2"
    
    }
    ```

    

**Delete User**

> DELETE /api/v1/people/{id}

- Parameters  

  `id ---- integer ---- required  `

- Response

  - Status 404
    
  ```json
    {
        "status": 404,
        "error": "Not Found",
        "message": "Person not found with id 2"
    
    }
    ```

    

**Update User By Id**

> PUT /api/v1/people/{id}

**Create User** 

>POST /api/v1/people

- Body 

  ```JSON
  {
      "firstName": "User",
      "lastName": "Update",
      "email": "novo@novo.com"
  }
  ```

- Response 

  - Status 201

    ```json
    {
        "message": "Person updated with ID 1"
    }
    ```

  - Status 400

    ```json
    {
    	"status": 400,
        "error": "Bad Request",
        "message": " "
    }
    ```

  - Status 404

    ```json
    {
    	"status": 404,
        "error": "Not Found",
        "message": "Person not found with id 2"
    
    }
    ```

    



## Topic

**Create Topic**

> POST /api/v1/topic

- Body 

  ```JSON
  {
      "question":"What's callback ?",
      "dateCreation":"03-08-2021",
      "subjectType":"JS",
      "author": {
          "id": 1,
          "firstName": "User",
          "lastName": "New",
          "email": "teste@teste.com"
      }
  }
  ```

- Response 

  - Status 201

    ```json
    {
        "message": "Topic was created with ID 1"
    }
    ```

  - Status 400

    ```json
    {
    	"status": 400,
        "error": "Bad Request",
        "message": " "
    }
    ```

    



**Find All Topics**

> GET /api/v1/topic

- Response

  - Status 200

    ```json
    [
        {
            "id": 1,
            "question":"What's callback ?",
            "dateCreation":"03-08-2021",
            "resolved": false,
            "subjectType": "JS",
            "answers": [],
            "author": {
                "id": 1,
                "firstName": "User",
                "lastName": "New",
                "email": "teste@teste.com"
            }
        }
    ]
    ```

    

**Find Topics By Subject** 

> GET /api/v1/topic/{subject}

- Parameters 

  `subject ---- SubjectType ----- requered`

    <details>
      <summary>SubjectType</summary>
      <ul>
        <li>JAVA</li>
        <li>PYTHON</li>
        <li>JS</li>
        <li>REACTJS</li>
        <li>MATH</li>
        <li>LOGIC</li>
      </ul>
    </details>

- Response
  - Status 200
  ```json
  [
    {
        "id": 5,
        "question": "org.springframework.dao.DataIntegrityViolationException: could not execute statement",
        "dateCreation": "2021-08-03",
        "resolved": false,
        "subjectType": "{subject}",
        "answers": [],
        "author": {
            "id": 1,
            "firstName": "Afonso",
            "lastName": "Neto",
            "email": "teste@teste.com"
        }
    }
  ]
  ```
  - Status 400
  ```json
  {
    "status": 400,
    "error": "Bad Request",
    "message": " "
  }
  ```

**Find Topics By Id**
> GET /api/v1/topic/id/{id}

- Parameters

  `id ---- integer ----- requered`

- Response
  - Status 200
  ```json
    {
        "id": "{id}",
        "question": "org.springframework.dao.DataIntegrityViolationException: could not execute statement",
        "dateCreation": "2021-08-03",
        "resolved": false,
        "subjectType": "JS",
        "answers": [],
        "author": {
            "id": 1,
            "firstName": "Afonso",
            "lastName": "Neto",
            "email": "teste@teste.com"
        }
    }
  ```
  - Status 404
  ```json
  {
    "status": 404,
    "error": "Not Found",
    "message": "Topic not found with ID 2"
  }
  ```

**Find Topics Resolved** *TO DO*

> GET /api/v1/topic/resolved

- Response
  - Status 200
  ```json
  [
    {
        "id": 1,
        "question": "org.springframework.dao.DataIntegrityViolationException: could not execute statement",
        "dateCreation": "2021-08-03",
        "resolved": true,
        "subjectType": "{subject}",
        "answers": [],
        "author": {
            "id": 1,
            "firstName": "Afonso",
            "lastName": "Neto",
            "email": "teste@teste.com"
        }
    }
  ]
  ```

**Find Topics Unresolved** *TO DO*

>  GET /api/v1/topic/unresolved

- Response
  - Status 200
  ```json
  [
    {
        "id": 1,
        "question": "org.springframework.dao.DataIntegrityViolationException: could not execute statement",
        "dateCreation": "2021-08-03",
        "resolved": true,
        "subjectType": "{subject}",
        "answers": [],
        "author": {
            "id": 1,
            "firstName": "Afonso",
            "lastName": "Neto",
            "email": "teste@teste.com"
        }
    }
  ]
  ```

**Add Answer** *TO DO*
>  POST /api/v1/topic/{id}

- Body

  ```json
  {
    "author": {
            "id": 6
        },
    "answer":"Default Answer",
    "topic": {
        "id": 5
    }
  }
  ```

- Response

  - Status 201

    ```json
    {
        "message": "Answer created with ID 1"
    }
    ```

  - Status 400

    ```json
    {
        "status": 400,
        "error": "Bad Request",
        "message": " "
    }
    ```

  ### Improvements
1. Search topic *TO DO*
2. Sort Answer by votes *TO DO*
3. Reply Answer *TO DO*
4. Like Answer *TO DO*
