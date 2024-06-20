# mySheet - README

## Project Description

**mySheet** is a web application written in Java using Spring framework. It is designed to check if we have enough materials to create new details.

![mySheet](https://github.com/Szackie/mySheet/assets/104226817/45d2f3f1-1530-4ee0-888a-ab640631b94b)

The program utilizes a simple nesting algorithm for rectangles (with the ability to rotate by 90 degrees). The algorithm selects the largest possible rectangles to execute, and in cases where rotation is possible, it chooses the option that generates a thinner scrap.

The program takes two input lists: the first one contains the dimentions of scraps and plates we have in stock, and the second one contains the ones to be created.
The algorithm checks how many new details can be manufactured and provides the result as output.

## Instructions

1. Paste the list with the dimensions of available sheets and details. Separate dimensions and quantities with any character and click "Add".

**NOTE 1:**

Do not specify quantities in the "Scrap List" window. The correct format is *width1* x *length1* *width2* x *length2*, etc.

**NOTE 2:**

Specify quantities in the "Details List" window. The correct format is: *width1* x *length1* x *QUANTITY1* *width2* x *length2* x *QUANTITY2*, etc.

2. Click the "Execute" button. The program will then display the possible elements to be manufactured.

The application uses a fast nesting algorithm. In extreme cases, there may be a possibility that it does not display all the feasible elements, but it will never produce an unachievable detail.
