# Go Ride

This repository is a final project (Java GUI) from Object-Oriented Programming Class, Teknik Informatika Universitas Padjadjaran. 

[Challenge Guidelines](challenge-guideline.md)

**Please create a description for this project here**

## Credits
| NPM           | Name                    |
| ------------- |-------------------------|
| 140810210019  | Raditya Muhamad Lacavi  |
| 140810210049  | Lazia Firli Adlisnandar |
| 140810210057  | Rakha Farras Maulana    |

## Change log
- **[Sprint Planning](changelog/sprint-planning.md) - SKIPPED** 

- **[Sprint 1](changelog/sprint-1.md) - (22/11/2022)** 
   - Planning
   - Installation (Gradle dan Java FX)

- **[Sprint 2](changelog/sprint-2.md) - (23/11/2022 to 29/11/2022)** 
   - Mencari Algoritma Pathfinding
   - Membuat grid yang interaktif dan bisa dimodifikasi
   - Membuat dan mementukan titik koordinat dengan sifat yang berbeda
   - Membuat Obstacle

- **[Sprint 3](changelog/sprint-3.md) - (30/11/2022 to 06/12/2022)** 
   - Titik Koordinat diterapkan dalam bentuk User dan Driver
   - Membuat Driver random yang nyata
   - Menghasilkan History Transaksi

## Running The App

Program berjalan dengan baik dan dapat memenuhi segala challenge-guideline yang diajukan. Namun, ada bug/error pada perubahan titik User dari sebeleum -> sesudah berangkat yang menyebabkan adanya gerakan tambahan yang seharusnya tidak perlu ada.

## Classes Used

- Model
   - Grid
   - ListRider
      - Rider
   - Tile

- PathfindingStrategy
   - DijkstraStrategy
   - PathfindingStrategy

- Painter

- View

- Controller

## Notable Assumption and Design App Details

- Banyak hal yang dapat dikembangkan kembali seperti: kemulusan UI, kemulusan gerakan/animasi
- Aplikasi sudah berjalan dengan baik selama User mengetahui konteks dari aplikasi ini
- Masih memiliki bug/error yang lumayan major
