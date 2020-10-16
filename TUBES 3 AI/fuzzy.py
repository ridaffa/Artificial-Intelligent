import csv          
f = open('DataTugas2.csv','r')
reader = csv.reader(f)

nomor = []
gaji = []
utang = []
for row in reader:
    nomor.append(int(row[0]))
    gaji.append (float(row[1]))
    utang.append(float(row[2]))

def up(x, a, b):
    return (x-a)/(b-a)      #rumus ketika grafik naik

def down(x, a, b):
    return (-1*(x-b)/(b-a))     #rumus ketika grafik turun

def gajiUp(gaji, a, b):
    return up(gaji, a, b)       #memasukan nilai gaji kedalam rumus naik

def gajiDown(gaji, a, b):
    return down(gaji,a ,b)       #memasukan nilai gaji kedalam rumus turun

def utangUp(utang,a ,b):
    return up(utang, a ,b)       #memasukan nilai utang kedalam rumus naik

def utangDown(utang, a, b):
    return down(utang, a, b)     #memasukan nilai utang kedalam rumus naik

def ystar(kaga,iye):
    return (((kaga*1)+(iye*2))/(kaga+iye))

def hasil_ystar(g,y):
    #print(g,y)
    if (g!=0 or y!=0):
        if ((ystar(g,y)>=0) and (ystar(g,y)<=1)):
            return "Tidak"
        elif ((ystar(g,y)>1) and (ystar(g,y)<=2)):
            return "Ya"
    else:
        return "Tidak"

"""VARIABLE Gaji"""

i=0         #sebagai increment
max=0
ya=0        #sebagai index ya
no=0        #sebagai index no
print("Keluarga      Gaji                Utang              BLT")
while (i<100):      #terdapat 100 data
    if (gaji[i]>=0) and (gaji[i]<=0.2):
        Mrendah = 1
        Msedang = 0
        Mcukup_tinggi = 0
        Mtinggi = 0
        Msangat_tinggi = 0
        tGaji = "Rendah"
    elif ((gaji[i]>0.2) and (gaji[i]<0.4)):
        a = 0.2
        b = 0.4
        Mrendah = gajiDown(gaji[i], a, b)
        Msedang = gajiUp(gaji[i], a, b)
        Mcukup_tinggi = 0
        Mtinggi = 0
        Msangat_tinggi = 0

        if (Mrendah <= Msedang):
            tGaji = "Rendah"
        else:
            tGaji = "Sedang"

    elif ((gaji[i]>=0.4) and (gaji[i]<=0.6)):
        Msedang = 1
        Mrendah = 0
        Mcukup_tinggi = 0
        Mtinggi = 0
        Msangat_tinggi = 0
        tGaji = "Sedang"
    elif ((gaji[i]>0.6) and (gaji[i]<0.8)):
        a = 0.6
        b = 0.8
        Mrendah = 0
        Msedang = gajiDown(gaji[i], a, b)
        Mcukup_tinggi = gajiUp(gaji[i], a, b)
        Mtinggi = 0
        Msangat_tinggi = 0

        if (Msedang <= Mcukup_tinggi):
            tGaji = "Sedang"
        else:
            tGaji = "Cukup_Tinggi"
    elif ((gaji[i]>=0.8) and (gaji[i]<=1)):
        Mcukup_tinggi = 1
        Mtinggi = 0
        Mrendah = 0
        Msedang = 0
        Msangat_tinggi = 0
        tGaji = "Cukup_Tinggi"
    elif ((gaji[i]>1) and (gaji[i]<1.2)):
        a = 1
        b = 1.2
        Mrendah = 0
        Msedang = 0
        Mcukup_tinggi = gajiDown(gaji[i], a, b)
        Mtinggi = gajiUp(gaji[i], a, b)
        Msangat_tinggi = 0

        if (Mcukup_tinggi <= Mtinggi):
            tGaji = "Cukup_Tinggi"
        else:
            tGaji = "Tinggi"
    elif ((gaji[i]>=1.2) and (gaji[i]<=1.4)):
        Mrendah = 0
        Msedang = 0
        Mcukup_tinggi = 0
        Mtinggi = 1
        Msangat_tinggi = 0
        tGaji = "Tinggi"
    elif ((gaji[i]>1.4) and (gaji[i]<1.6)):
        a = 1.4
        b = 1.6
        Mrendah = 0
        Msedang = 0
        Mcukup_tinggi = 0
        Mtinggi = gajiDown(gaji[i], a, b)
        Msangat_tinggi = gajiUp(gaji[i], a, b)

        if (Mtinggi <= Msangat_tinggi):
            tGaji = "Tinggi"
        else:
            tGaji = "Sangat_Tinggi"
    elif ((gaji[i]>=1.6)):
        Mrendah = 0
        Msedang = 0
        Mcukup_tinggi = 0
        Mtinggi = 0
        Msangat_tinggi = 1
        tGaji = "Sangat_Tinggi"

    """UTANG"""
    if ((utang[i]>=0) and (utang[i]<=25)):
        Mlow = 1
        Mmid = 0
        Mhigh = 0
        tUtang = "Low"
    elif ((utang[i]>25) and (utang[i]<50)):
        a = 25
        b = 50
        Mlow = utangDown(utang[i], a, b)
        Mmid = utangUp(utang[i], a, b)
        Mhigh = 0
        if (Mlow<=Mmid):
            tUtang = "Low"
        else:
            tUtang = "Mid"

    elif ((utang[i]>=50) and (utang[i]<=75)):
        Mlow = 0
        Mmid = 1
        Mhigh = 0
        tUtang = "Mid"
    elif ((utang[i]>75) and (utang[i]<100)):
        a = 75
        b = 100
        Mlow = 0
        Mmid = utangDown(utang[i], a, b)
        Mhigh = utangUp(utang[i], a, b)
        if (Mmid<=Mhigh):
            tUtang = "Mid"
        else:
            tUtang = "High"
    elif ((utang[i]>=100)):
        Mlow = 0
        Mmid = 0
        Mhigh = 1
        tUtang = "High"

    if (tGaji == "Rendah"):
        if (tUtang == "Low"):
            if (Mrendah<Mlow):
                tCross = Mrendah
            elif (Mrendah>=Mlow):
                tCross = Mlow

            if (tCross>ya):
                ya = tCross
        elif (tUtang == "Mid"):
            if (Mrendah<Mmid):
                tCross = Mrendah
            elif (Mrendah>=Mmid):
                tCross = Mmid

            if (tCross > ya):
                ya = tCross
        elif (tUtang == "High"):
            if (Mrendah<Mhigh):
                tCross = Mrendah
            elif (Mrendah>=Mhigh):
                tCross = Mhigh

            if (tCross > ya):
                ya = tCross
    elif (tGaji == "Sedang"):
        if (tUtang == "Low"):
            if (Msedang<Mlow):
                tCross = Msedang
            elif (Msedang>=Mlow):
                tCross = Mlow

            if (tCross > ya):
                ya = tCross
        elif (tUtang == "Mid"):
            if (Msedang<Mmid):
                tCross = Msedang
            elif (Msedang>=Mmid):
                tCross = Mmid

            if (tCross > ya):
                ya = tCross
        elif (tUtang == "High"):
            if (Msedang<Mhigh):
                tCross = Msedang
            elif (Msedang>=Mhigh):
                tCross = Mhigh

            if (tCross > ya):
                ya = tCross
    elif (tGaji == "Cukup_Tinggi"):
        if (tUtang == "Low"):
            if (Mcukup_tinggi<Mlow):
                tCross = Mcukup_tinggi
            elif (Mcukup_tinggi>=Mlow):
                no = tCross

            if (tCross > no):
                no=tCross
        elif (tUtang == "Mid"):
            if (Mcukup_tinggi<Mmid):
                tCross = Mcukup_tinggi
            elif (Mcukup_tinggi>=Mmid):
                tCross = Mmid

            if (tCross > ya):
                ya = tCross
        elif (tUtang == "High"):
            if (Mcukup_tinggi<Mhigh):
                tCross = Mcukup_tinggi
            elif (Mcukup_tinggi>=Mhigh):
                tCross = Mhigh
            if (tCross > ya):
                ya = tCross
    elif (tGaji == "Tinggi"):
        if (tUtang == "Low"):
            if (Mtinggi<Mlow):
                tCross = Mtinggi
            elif (Mtinggi>=Mlow):
                tCross = Mlow
            if (tCross > no):
                no = tCross
        elif (tUtang == "Mid"):
            if (Mtinggi<Mmid):
                tCross = Mtinggi
            elif (Mtinggi>=Mmid):
                tCross = Mmid

            if (tCross > no):
                no = tCross
        elif (tUtang == "High"):
            if (Mtinggi<Mhigh):
                tCross = Mtinggi
            elif (Mtinggi>=Mhigh):
                tCross = Mhigh
            if (tCross > ya):
                ya = tCross
    elif (tGaji == "Sangat_Tinggi"):
        if (tUtang == "Low"):
            if (Msangat_tinggi<Mlow):
                tCross = Msangat_tinggi
            elif (Msangat_tinggi>=Mlow):
                tCross = Mtinggi
            if (tCross > no):
                no=tCross
        elif (tUtang == "Mid"):
            if (Msangat_tinggi<Mmid):
                tCross = Msangat_tinggi
            elif (Msangat_tinggi>=Mmid):
                tCross = Mmid
            if (tCross > no):
                no = tCross
        elif (tUtang == "High"):
            if (Msangat_tinggi<Mhigh):
                tCross = Msangat_tinggi
            elif (Msangat_tinggi>=Mhigh):
                tCross = Mhigh
            if (tCross > no):
                no = tCross

    print( nomor[i],"      |     ",gaji[i],"   |       ",utang[i],"    |     ",hasil_ystar(no,ya))
    i = i+1
    ya=0
    no=0

    if (hasil_ystar(no,ya)=="Ya"):
        with open('TebakanTugas2.csv',mode='w') as csv_file:
            fieldnames = ['Keluarga ke-']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            writer.writeheader()
            writer.writerow({nomor[i]}

                )
