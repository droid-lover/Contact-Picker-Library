# Contact-Picker-Library

This Code explain how to pick the contact with the help of ContactsContract.CommonDataKinds 

Actually This all is happening with ContentProvider 

with contentResolver class , we are dispatching the Content.URI to the content provider , 

Content.URI = the path of any specific table , contentprovider use paths called URI to access a 
unique table with a unique path means unique URI.

And then with cursor we get the slected position , and on basis of that we just find that index number Username and PhoneNumber.