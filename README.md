<h1 align="left"> Project 1-1: Cargo Loader | Phase 3</h1>
<p align="left">Made by Group 07</p><br>
<p align="left"><img src="https://i.imgur.com/CqXvA9C.png"></p>
<h2 align="left">Introduction</h2><br>
<p>
  The Cargo Loader Program was created in the context of the third phase of project 1-1. It is a program that aims to solve the three dimensional packing problem. The program aims to load a cargo container of the size 16.5m x 2.5m x 4m with parcels of three different sizes:
<ul>
  <li>Parcel A: 1m x 1m x 2m</li>
  <li>Parcel B: 1m x 1.5m x 2m</li>
  <li>Parcel C: 1.5m x 1.5m x 1.5m</li>
</ul>
</p><br>
<h2 align="left">Algorithm</h2><br>
<p>
  In order to tackle this problem the cargo space and parcels are represented as three-dimensional matrices. Each package type is placed in the container in each possible position and the resulting matrix is flattened. All of the resulting rows are combined to a big matrix and Donald Kuth's Algorithm X is used in order to find an exact cover such that the cargo space is completely filled. It uses a dancing links implementation to ensure better efficiency.
</p><br>
<h2 align="left">Running the Program</h2><br>
<p>
  In order to run the program follow these steps:
<ol>
  <li>Clone this github repository</li>
  <li>Make sure you have javaFX installed since it is a dependency</li>
  <li>Navigate into the CargoLoader directory</li>
  <li>Run the Prorgram.java file</li>
</ol>
</p><br>
<h2 align="left">Authors</h2><br>
