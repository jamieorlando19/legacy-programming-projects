/*
Inpost.cpp
Jamie Orlando
Assigned: 3/19/99
Due: 4/9/99
Worth: 80 points

Purpose:  To input an expression in infix notation, convert it to postfix
        notation and then evaluate the expression.
*/

#include<iostream.h>
#include<conio.h>
#include"apstack.h"
#include"apstring.cpp"

void input(apstring &post, apstring &in);
double evaluatepost(apstring post);
void print(apstring in, apstring post, double result, char &run);

int main()
{
	char runagain = ' ';     //Character which controls the program loop
   double answer;           //The final result of the equation
   
   do
   {
   	apstring infix;          //String which is the infix equation
   	apstring postfix;        //String which is the converted postfix notation

		cout << "Jamie Orlando's Infix to Postfix converter\n";
      cout << "------------------------------------------\n\n";
      input(postfix, infix);
      answer = evaluatepost(postfix);
      print(infix, postfix, answer, runagain);
   	clrscr();
   }while(runagain == 'y' || runagain == 'Y');

   cout << "Bye!";
   getch();
   return 0;
}

void input(apstring &post, apstring &in)
//Purpose: To input a valid infix expression and convert it to postfix
//Pre: in and post are apstrings for the infix and postfix equation. Legal
//     operators are +,-,* and /.  Parenthesis may be used. Operands must be
//     integers
//Post: The infix and postfix equations will be returned
{
	apstack<char> operators;    //Stack which holds operators and paranthesis
   char temp;                  //Temporary character

   cout << "Enter your expression in infix notation: ";
   cin >> in;

   for(int e = 0; e < in.length(); ++e)
   {
   	switch(in[e])
      {
			case '(':   operators.push(in[e]);
         	break;
         case ')':	while(operators.top() != '(')
         			   {
                     	operators.pop(temp);
                        post += temp;
                     }
                     operators.pop();
         	break;
         case('*'):
         case('/'):	while(!operators.isEmpty() && (operators.top() == '*' || operators.top() == '/'))
         				{
                        operators.pop(temp);
                        post += temp;
                     }
                     operators.push(in[e]);
         	break;
         case('+'):
         case('-'):	while(!operators.isEmpty() && operators.top() != '(')
         				{
                     	operators.pop(temp);
                        post += temp;
                     }
                     operators.push(in[e]);
         	break;
         default:    if(post.length() != 0)
         				  post += in[e];
                     else
                  	  post = in[e];
      }
   }
   while(!operators.isEmpty())
   {
   	operators.pop(temp);
      post += temp;
   }
}

double evaluatepost(apstring post)
//Purpose: To take the postfix equation and evaluate it, returning the result
//Pre: The postfix expression must have been derived
//Post: The result which is a real number will be returned
{
	apstack<double> s;    //A stack which does the operations
   double temp;          //Temporary double variable

   for(int a = 0; a < post.length(); ++a)
   {
      if (post[a] == '+' || post[a] == '-' || post[a] == '*' || post[a] == '/')
      {
         s.pop(temp);
			switch(post[a])
      	{
         	case '+':	temp += s.top();
         		break;
         	case '-':	temp = s.top() - temp;
         		break;
				case '*':	temp *= s.top();
         		break;
				case '/':	temp = s.top() / temp;
      	}
         s.pop();
         s.push(temp);
		}
      else
      {
      	temp = double(int(post[a]) - 48);
         s.push(temp);
      }
   }

   return s.top();
}

void print(apstring in, apstring post, double result, char &run)
//Purpose: To print out the infix and postfix notations and the result, and to
//         ask if the user wishes to run the program again
//Pre: Infix and Postfix equations are in strings and the result is a string
//Post: Data will be outputted and the character which controls the program loop
//      will be returned
{
   cout << "\n\nYou entered: \n" << in;
   cout << "\nThe postfix equivalent of this expression is: \n" << post;
   cout << "\nThe result of this is: " << result;
   cout << "\n\n\nDo you wish to run this program again? ";
   cin >> run;
}

