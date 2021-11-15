# Questionnaire 

### Are there any sub-optimal choices( or short cuts taken due to limited time ) in your implementation?

---

Due to the time limitation, I used a frontend library called Material UI to aid in designing the UI. In addition, I have referenced my previous project code for styling using styled-components.

### Is any part of it over-designed? ( It is fine to over-design to showcase your skills as long as you are clear about it)

---

Nothing was particularly over-designed but there were features that I would like to add in the future. Instead of dealing with only two types of cryptocurrency and exchanges, I would like to add multiple of them. The application is designed in a way that many more coins and exchanges could be easily added. 

### If you have to scale your solution to 100 users/second traffic what changes would you make, if any?

---

To handle the traffic, I would use a load balancer such as NGINX to create multiple instances of a servers. Later on, if the application gets more users, I would like to refactor the application architecture from monolith to microservices. 

### What are some other enhancements you would have made, if you had more time to do this implementation

---

If I had more time, I would have made several improvements. First, the UI could be improved by adding a functional navigation bar and making the application more responsive for mobile uses. I also would like to improve my backend server by adding more tests to solidify my logic. In addition, I would like to improve the docker scripts so that they are more automated and easy to use. Lastly, I would like to deploy my application with CI/CD using Jenkins. I have just began to study about Docker and Jenkins, so I couldn't add to the project due to the time limitation.  