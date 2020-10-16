#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy
import pandas
import csv


# # **BACA DATA**

# In[18]:


def readFile():
    filenya = open("influencers.csv", "r")
    d = filenya.readlines()
    
    array = []


    for i in range(1, len(d)):
      array.append(d[i].replace('\n','').split(','))
    
    return array


# # **FUZZIFIKASI**

# Atribut Followers

# In[19]:


def fuzzificationFollowers():
  data = readFile()
  followers = []
 

  Upper = 70000
  NotUpper = 55000
  Middle1 = 40000
  Middle2 = 50000
  Bottom = 20000
  NotBottom = 30000

  for i in range(len(data)):
    
    x = int(data[i][1])
    
    if (x > Upper):
      Up = 1
    elif (x <= NotUpper):
      Up = 0
    else:
      Up = round((x-NotUpper)/(Upper-NotUpper),2)

    
    if (x <= Bottom):
      Bot = 1
    elif (x > NotBottom):
      Bot = 0
    else:
      Bot = round((NotBottom-x)/(NotBottom-Bottom),2)

    
    if ((x > Middle1) & (x <= Middle2)):
      Mid = 1
    elif ((x <= Bottom) or (x > Upper)):
      Mid = 0
    else:
      if (x > Middle2):
        Mid = round((Upper-x)/(Upper-Middle2),2)
      elif (x <= Middle1):
        Mid = round((x-Bottom)/(Middle1-Bottom),2) 
    
    fol = []
    foll = []
    fol.append(Up)
    fol.append('upper')
    foll.append(fol)
    fol = []
    fol.append(Mid)
    fol.append('middle')
    foll.append(fol)
    fol = []
    fol.append(Bot)
    fol.append('bottom')
    foll.append(fol)
    fol = []
    followers.append(foll)
  return followers


# Atribut Engagement Rate

# In[20]:


def fuzzificationEngageRate():
  data = readFile()
  EngageRate = []
  
  High = 7
  NotHigh = 5.5
  Average1 = 4
  Average2 = 5
  Low = 2
  NotLow = 3

  for i in range(len(data)):
    
    x = float(data[i][2])
    
    if (x > High):
      Hi = 1
    elif (x <= NotHigh):
      Hi = 0
    else:
      Hi = round((x-NotHigh)/(High-NotHigh),2)

    
    if (x <= Low):
      Lo = 1
    elif (x > NotLow):
      Lo = 0
    else:
      Lo = round((NotLow-x)/(NotLow-Low),2)

    
    if ((x > Average1) & (x <= Average2)):
      Avg = 1
    elif ((x <= Low) or (x > High)):
      Avg = 0
    else:
      if (x > Average2):
        Avg = round((High-x)/(High-Average2),2)
      elif (x <= Average1):
        Avg = round((x-Low)/(Average1-Low),2)
    
    rat = []
    rate = []
    rat.append(Hi)   
    rat.append('high')
    rate.append(rat)
    rat = []
    rat.append(Avg)  
    rat.append('average')
    rate.append(rat)
    rat = []
    rat.append(Lo)   
    rat.append('low')
    rate.append(rat)
    rat = []
    EngageRate.append(rate)
  
  return EngageRate


# # **RULE INFERENSI**

# In[21]:


def inferensi():

  followers = fuzzificationFollowers()
  engageRate = fuzzificationEngageRate()
  concs = []

  for i in range(len(followers)):
    rules = [[followers[i][0],engageRate[i][0]],
             [followers[i][0],engageRate[i][1]],
             [followers[i][0],engageRate[i][2]],
             [followers[i][1],engageRate[i][0]],
             [followers[i][1],engageRate[i][1]],
             [followers[i][1],engageRate[i][2]],
             [followers[i][2],engageRate[i][0]],
             [followers[i][2],engageRate[i][1]],
             [followers[i][2],engageRate[i][2]]]
    
    Chosen = []
    Considered = []
    Rejected = []
    for z in range(len(rules)):
      if (rules[z][0][1] == 'upper'):
        if (rules[z][1][1] == 'high'):
          Chosen.append(z)
        elif (rules[z][1][1] == 'average'):
          Chosen.append(z)
        elif (rules[z][1][1] == 'low'):
          Considered.append(z)

      elif (rules[z][0][1] == 'middle'):
        if (rules[z][1][1] == 'high'):
          Chosen.append(z)
        elif (rules[z][1][1] == 'average'):
          Considered.append(z)
        elif (rules[z][1][1] == 'low'):
          Rejected.append(z)

      elif (rules[z][0][1] == 'bottom'):
        if (rules[z][1][1] == 'high'):
          Considered.append(z)
        elif (rules[z][1][1] == 'average'):
          Rejected.append(z)
        elif (rules[z][1][1] == 'low'):
          Rejected.append(z)
    
    for x in range(len(rules)):
      minn = min(rules[x])
      rules[x].append(minn)

    chsn = max(rules[Chosen[0]][2][0],rules[Chosen[1]][2][0],rules[Chosen[2]][2][0])
    csdr = max(rules[Considered[0]][2][0],rules[Considered[1]][2][0],rules[Considered[2]][2][0])
    rejc = max(rules[Rejected[0]][2][0],rules[Rejected[1]][2][0],rules[Rejected[2]][2][0])
    conc = []
    conc.append(chsn)
    conc.append(csdr)
    conc.append(rejc)
    concs.append(conc)
 
  return concs


# # **DEFUZZIFIKASI**

# In[11]:


def defuzzification():
  inf = inferensi()
  hasilDef = []

  Chosen = 100
  Considered = 70
  Rejected = 50

  for i in range(len(inf)):
    Z = (Chosen*inf[i][0])+(Considered*inf[i][1])+(Rejected*inf[i][2])
    hasilDef.append(round(Z/sum(inf[i]),2))


  return hasilDef


# # **SORTING**

# In[17]:


def InfluencerTerbaik():
  defuzzi = defuzzification()
  data = readFile()
  
  bestt = []

  i = 0
  sorting = numpy.argsort(defuzzi)[::-1]
  while i in range(0,20):
    best = []
    best.append(data[sorting[i]][0])
    bestt.append(best)
    i = i + 1
  return bestt
InfluencerTerbaik()


# # **WRITE TO CSV**

# In[22]:


def writeCSV():
  top = InfluencerTerbaik()
  f = open('hasil.csv', 'w')
  w = csv.writer(f)
  w.writerow(('Nomor Record',))
  w.writerows(top)

writeCSV()

