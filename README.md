# Formatter Eclipse Server

Code formatter that formats a code snippet according to Check Style profiles.

A headless Eclipse that listens to a port (8846).

It receives a POST request with the following fields:

code: the source code to reformat (mandatory)
how: either "Compact", "Eclipse" or "Vertical"  (optional, defaults to "Compact")
format: either "ui" or "text", (optional, defaults to "ui")
line-length: integer containing the width (optional, defaults to -1)

Depending on the value of "format" the output will be a plain/text
(text) or text/html (ui) version of the formatted code.

## Installation

(Tested on Eclipse 4.5 for RCP )

Generate a plugin for https://github.com/annieying/Formatter,
following the instructions in the project.

Create a workspace someplace outside this folder.

In eclipse, go to project / import / existing projects into workspace

Select as root directory the *parent* directory for the folder containing this project.

Import it.

Eclipse will then compile it. If there are any errors check whether
you have the right JRE installed.

Once it has finished building, if you have also Formatter in the
workspace, you can run it directly from the Run menu.

You can then test it from the shell using curl:

curl -XPOST --data 'code=class HelloWorld { public static void main(String[]args){ System.out.println("hello w; }}' http://localhost:8846/Formatter?format=text

it should return:

class HelloWorld {
  public static void main(
      String[] args)
  {
    System.out.println("hello world!");
  }

Or you can test it in a browser: http://localhost:8846/Formatter

TODO: Exporting to a jar and executing standalone