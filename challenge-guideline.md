# Challenge Guidelines

A. Deskripsi masalah
- Nama main file adalah 'go-ride' (Contoh command: java go-ride)
- Secara default akan membentuk 20 x 20 map pada drawing board. 
- Dengan menggunakan args (manfaatkan argument String[] args pada method main), tambahkan argumen 'n' sebagai ukuran custom map (misal 50x50) (Contoh command: java go-ride 50) 
- Taruh 5 driver pada random koordinat. Taruh 1 user pada random koordinat.

B. Setelah app berjalan
- Tampilkan map dalam bentuk drawing board 
- Buatlah perbedaan indikator map, user, dan driver pada map
- Buat input koordinat kemana user akan pergi
- Buat button untuk konfirmasi koordinat. Setelah menekan konfirmasi akan muncul:
    - Nama driver terdekat
    - Price estimation
    - Rute jalan user menuju tempat tujuan pada drawing board
    - Button lain untuk proceed layanan go-ride
- Buat asumsi jika driver dan user tidak bisa berjalan secara diagonal. Sebagai contoh jika user ingin pergi dari (1,1) ke (2,2): startAt(1,1), goTo(1,2), turnRight(), goTo(2,2), finishAt(2,2)
- Setelah order selesai,pindahkan user ke koordinat yang dituju, kemudian pindahkan driver yang telah melayani ke random coordinate.
- Buat button untuk "Order again" untuk order kembali dengan posisi user sekarang.
- Buat button history untuk melihat history user.

C. Batasan lainnya
- Data history akan direset ketika aplikasi ditutup
- Jika ada batasan atau fitur tambahan lain, tolong deskripsikan kepada asprak (akan menjadi nilai lebih)

D. Challenge ideas (optional)
- Buat animasi ketika driver menghampiri user
- Buat animasi ketika driver mengantar user