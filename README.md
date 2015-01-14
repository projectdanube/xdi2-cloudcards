<a href="http://projectdanube.org/" target="_blank"><img src="http://projectdanube.github.com/xdi2/images/projectdanube_logo.png" align="right"></a>
<img src="http://projectdanube.github.com/xdi2/images/logo64.png"><br>

xdi2-cloudcards
==============

xdi2-cloudcards is a demo application which allow you to share Cloud Cards. This cards can include public or private data. In order to have a see the private data, you have to request permission to the cloud owner.

To create new cards, you can use the Cloud Card editor included in the [xdi2-manager](https://github.com/projectdanube/xdi2-manager) project.

Card example: https://cloud-cards.xdi2.org/=andrepm

Website: https://xdi2.org/, sample deployment: https://cloud-cards.xdi2.org/

### How to build
First, you need to build the main [XDI2](https://github.com/projectdanube/xdi2) project.

After that, just run

    mvn clean install

That will generate xdi2-cloudcards.war. Deploy it in your favorite Java Servlet container.

### Community

Website: https://xdi2.org/

Google Group: http://groups.google.com/group/xdi2
