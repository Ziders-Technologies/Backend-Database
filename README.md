# Backend-Database
Key- Value Based Data store

JSON file creation, I have created the file in this -"E:\\Datastore.json" destination. If your computer fails to have the respective Drive/Local disk name,you have to modify the path mentioned in the FileCreate.java . 

Conditions:-
1.Insertion :-JSON key is always a string - capped at 32chars.
2.Duplicate JSON key not accepted. (If Create is invoked for an existing key, an appropriate error is thrown).
3.A Delete operation can be performed by providing the key only.
4.Time-To-Live based on the updation system time. 
5. Thread safe is used in such way if file is accessed by JVM ,that file cannot be accessed.


This file works on operating system which has JDK.
I have used IntelliJ IDE to develop this project.
If you are using the same ItelliJ IDE you can just download the datastore folder from here and you can import that file from your saved location to the IDE.
Appropriate error responses must always be returned to a client if it uses the data store in unexpected ways.


