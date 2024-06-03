# TodoBuddy

TodoBuddy adalah aplikasi todo list yang dirancang untuk membantu pengguna mengelola berbagai tugas dan kegiatan dalam kehidupan sehari-hari. 
Aplikasi ini sangat fleksibel dan dapat digunakan untuk berbagai keperluan, mulai dari mengelola tugas sekolah, kegiatan kuliah, hingga urusan sehari-hari yang bersifat umum.

## Fitur Utama

- **Manajemen Tugas:** Pengguna dapat menambah, mengedit, dan menghapus tugas atau kegiatan sehari-hari.
- **Halaman Register dan Login:** Pengguna dapat membuat akun dan login untuk mengakses aplikasi.
- **Halaman Home:** Menampilkan daftar tugas atau kegiatan yang telah ditambahkan oleh pengguna.
- **Halaman API:** Menampilkan contoh tampilan tugas atau kegiatan dengan data yang diambil dari API eksternal.
- **Halaman Profil:** Menampilkan informasi pribadi pengguna dan tombol logout.
- **Splashscreen:** Menyambut pengguna ketika aplikasi pertama kali dibuka.

## Teknologi yang Digunakan

- **SQLite:** Untuk penyimpanan data tugas lokal.
- **Navigation Component:** Untuk navigasi antara berbagai halaman dan fragmen.
- **SharedPreferences:** Untuk menyimpan informasi login pengguna.
- **Retrofit:** Untuk penembakan API dan pengambilan data dari API server.
- **API Server:** Menggunakan API dari [https://freetestapi.com/apis/todos](https://freetestapi.com/apis/todos).

## Struktur Aplikasi

### Activities

1. **SplashscreenActivity:** Menampilkan layar pembuka saat aplikasi pertama kali dijalankan.
2. **RegisterActivity:** Untuk pendaftaran pengguna baru.
3. **LoginActivity:** Untuk masuk ke dalam aplikasi menggunakan akun yang sudah terdaftar.
4. **MainActivity:** Halaman utama yang berisi navigasi ke berbagai fragmen.
5. **AddActivity:** Untuk menambahkan tugas atau kegiatan baru.
6. **UpdateActivity:** Untuk mengedit atau menghapus tugas yang sudah ada.

### Fragments

1. **HomeFragment:** Menampilkan daftar tugas atau kegiatan yang sudah ditambahkan.
2. **ApiFragment:** Menampilkan contoh data tugas atau kegiatan dari API eksternal. Data ini bersifat statis dan tidak dapat diubah.
3. **ProfileFragment:** Menampilkan informasi pribadi pengguna seperti nama dan username, serta tombol logout.

## Cara Penggunaan

1. **Install Aplikasi:** Clone repository ini dan buka di Android Studio.
2. **Register Akun:** Buka aplikasi dan daftar menggunakan halaman register dengan mengisi informasi seperti nama dan username.
3. **Login:** Setelah berhasil mendaftar, login menggunakan informasi akun yang telah dibuat.
4. **Tambah Tugas:** Pada halaman home, tambahkan tugas atau kegiatan menggunakan halaman add.
5. **Edit/Hapus Tugas:** Klik item tugas pada halaman home untuk mengedit atau menghapus tugas menggunakan halaman update.
6. **Lihat Data API:** Buka halaman API untuk melihat contoh tampilan tugas atau kegiatan yang diambil dari API eksternal.
7. **Profil Pengguna:** Buka halaman profile untuk melihat informasi pribadi dan logout dari aplikasi.

## Instalasi

1. **Clone Repository:**

   ```bash
   git clone https://github.com/username/TodoBuddy.git
   ```

2. **Buka di Android Studio:**
   - Pilih 'Open an existing Android Studio project' dan pilih folder `TodoBuddy`.

3. **Bangun Proyek:**
   - Pastikan semua dependensi telah diunduh dan proyek berhasil dibangun tanpa kesalahan.

## Dependencies

- SQLite
- Navigation Component
- SharedPreferences
- Retrofit
- LottieFiles

## API Reference

- **Base URL:** [https://freetestapi.com/apis/todos](https://freetestapi.com/apis/todos)

## Kontribusi

Jika Anda ingin berkontribusi pada proyek ini, silakan fork repositori ini dan ajukan pull request. Pastikan untuk mengikuti pedoman kontribusi yang telah ditetapkan.

## Lisensi

Proyek ini dilisensikan di bawah lisensi MIT. Lihat file LICENSE untuk detail lebih lanjut.

