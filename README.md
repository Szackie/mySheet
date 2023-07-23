# mySheet
Web application for checking if we have enough materials to create new details.

The program utilizes a simple nesting algorithm for rectangles (with the ability to rotate by 90 degrees). The algorithm selects the largest possible rectangles to execute, and in cases where rotation is possible, it chooses the option that generates a thinner scrap.

The program takes two input lists: the first one contains the dimentions of scraps and plates we have in stock, and the second one contains the ones to be created.
The algorithm checks how many new details can be manufactured and provides the result as output.
