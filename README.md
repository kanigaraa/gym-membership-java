<div align="center">

# 🏋️ Gym Membership App

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Java%20Swing-GUI-blue?style=for-the-badge)
![OOP](https://img.shields.io/badge/OOP-Object%20Oriented%20Programming-purple?style=for-the-badge)

Aplikasi desktop berbasis Java untuk mengelola data membership gym secara sederhana, rapi, dan terstruktur.

</div>

---

## 📌 Deskripsi Project

**Gym Membership App** adalah aplikasi desktop yang dibuat untuk membantu admin gym dalam mengelola data member, paket membership, status keanggotaan, dan pembayaran.

Aplikasi ini dikembangkan menggunakan bahasa pemrograman **Java** dengan menerapkan konsep **Object Oriented Programming (OOP)**. Selain itu, aplikasi juga menggunakan **GUI Java** agar pengguna dapat berinteraksi dengan program melalui tampilan visual, serta menggunakan **database MySQL** untuk menyimpan dan menampilkan data member.

Project ini dibuat sebagai tugas akhir mata kuliah **Pemrograman Berorientasi Objek**.

---

## 🎯 Tujuan Aplikasi

Tujuan dari aplikasi ini adalah:

- Mempermudah proses pencatatan data member gym.
- Mengelola jenis paket membership.
- Menampilkan data member yang tersimpan di database.
- Membantu admin mengetahui status keanggotaan member.
- Menerapkan konsep OOP dalam pengembangan aplikasi Java.

---

## ✨ Fitur Utama

- Login admin
- Tambah data member
- Edit data member
- Hapus data member
- Tampilkan daftar member
- Pilih paket membership
- Kelola status membership
- Menampilkan data dari database
- Interface berbasis GUI Java

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|---|---|
| Java | Bahasa pemrograman utama |
| Java Swing | Membuat tampilan GUI |
| MySQL | Database penyimpanan data |
| JDBC | Menghubungkan Java dengan MySQL |
| IntelliJ IDEA | IDE pengembangan aplikasi |
| Git & GitHub | Version control dan penyimpanan source code |

---

## 🗂️ Struktur Folder

```bash
gym-membership/
│
├── src/
│   ├── Main.java
│   ├── config/
│   │   └── DatabaseConnection.java
│   │
│   ├── model/
│   │   ├── Member.java
│   │   ├── RegularMember.java
│   │   └── PremiumMember.java
│   │
│   ├── view/
│   │   ├── LoginFrame.java
│   │   └── DashboardFrame.java
│   │
│   ├── controller/
│   │   └── MemberController.java
│   │
│   └── dao/
│       └── MemberDAO.java
│
├── README.md
├── .gitignore
└── gym-membership.iml
