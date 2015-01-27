<a href="http://projectdanube.org/" target="_blank"><img src="http://projectdanube.github.com/xdi2/images/projectdanube_logo.png" align="right"></a>
<img src="http://projectdanube.github.com/xdi2/images/logo64.png"><br>

XDI Cloud Card viewer [https://cloud-cards.xdi2.org/](https://cloud-cards.xdi2.org/)

This web application displays XDI Cloud Cards, which may include public or private data. In order to access the private data, a "connection invitation" from an
authorizing authority (AA) to a requesting authority (RA) is issued. This happens via an "XDI Connect" button on the Cloud Card.

To create new Cloud Cards, you can use the [Cloud Manager](https://github.com/projectdanube/xdi2-manager) project.

Card example: https://cloud-cards.xdi2.org/=andrepm

### Information

* [Walkthrough](https://github.com/projectdanube/xdi2-cloudcards/wiki/Walkthrough)
* [Screencast](https://github.com/projectdanube/xdi2-cloudcards/wiki/Screencast)

### How to build

First, you need to build the main [XDI2](https://github.com/projectdanube/xdi2) project.

After that, just run

    mvn clean install

This will generate .war file ready for deployment.

### Community

Website: https://xdi2.org/

Google Group: http://groups.google.com/group/xdi2
