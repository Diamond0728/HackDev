<template>
  <div>
    <v-layout row wrap>
      <v-flex hidden-xs-only md8 lg8 class="pa-1">
        <v-card height="600px">
          地图
        </v-card>
      </v-flex>
      <v-flex xs12 md4 lg4 class="pa-1">
        <v-card height="600px">
          <p>输入特征值</p>
          <div class="ma-2">
            <v-text-field label="地址" color="green"></v-text-field>
            <v-text-field label="地址" color="green"></v-text-field>
            <v-text-field label="地址" color="green"></v-text-field>
            <v-text-field label="地址" color="green"></v-text-field>
            <v-text-field label="地址" solo color="green"></v-text-field>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>
