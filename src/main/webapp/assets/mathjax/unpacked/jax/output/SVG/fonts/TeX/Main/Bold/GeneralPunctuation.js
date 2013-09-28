/*************************************************************
 *
 *  MathJax/jax/output/SVG/fonts/TeX/svg/Main/Bold/GeneralPunctuation.js
 *
 *  Copyright (c) 2011 Design Science, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

MathJax.Hub.Insert(
  MathJax.OutputJax.SVG.FONTDATA.FONTS['MathJax_Main-bold'],
  {
    // EN DASH
    0x2013: [300,-249,575,0,574,'0 249V300H574V249H0'],

    // EM DASH
    0x2014: [300,-249,1150,0,1149,'0 249V300H1149V249H0'],

    // LEFT SINGLE QUOTATION MARK
    0x2018: [695,-329,319,58,246,'58 461Q58 503 70 542T99 607T134 654T165 684T184 694T201 683T213 664Q213 658 202 648T175 624T143 583T116 518Q115 512 114 505T112 493L111 488Q132 500 161 500Q198 500 221 475T245 414T222 354T161 329Q112 329 85 369T58 461'],

    // RIGHT SINGLE QUOTATION MARK
    0x2019: [694,-329,319,74,261,'74 572T74 608T97 669T157 694Q203 694 232 657T261 559Q261 520 250 482T222 418T187 370T155 339T135 329Q128 329 117 340T106 359Q106 365 117 375T144 399T176 440T203 505Q204 511 205 518T208 530V535L202 532Q196 530 184 527T158 523Q121 523 98 547'],

    // LEFT DOUBLE QUOTATION MARK
    0x201C: [694,-329,603,110,564,'110 461Q110 502 121 541T150 606T185 653T217 684T235 694Q242 694 254 682T266 664Q266 659 254 648T226 623T193 578T167 511Q164 500 164 494T164 487Q188 500 212 500Q251 500 274 475T297 414Q297 378 274 354T212 329Q167 329 139 367T110 461ZM377 461Q377 502 388 541T417 606T452 653T484 684T502 694Q509 694 521 682T533 664Q533 659 521 648T493 623T460 578T434 511Q431 500 431 494T431 487Q455 500 479 500Q518 500 541 475T564 414Q564 378 541 354T479 329Q434 329 406 367T377 461'],

    // RIGHT DOUBLE QUOTATION MARK
    0x201D: [694,-328,603,38,492,'38 572T38 608T61 669T121 694Q167 694 196 657T225 559Q225 520 214 482T186 418T151 370T119 339T99 329T82 340T70 360Q70 365 74 369T92 385T122 414Q142 441 154 471T170 518L172 535L166 532Q160 530 148 527T122 523Q85 523 62 547ZM305 572T305 608T328 669T388 694Q434 694 463 657T492 559Q492 520 481 482T453 418T418 370T386 339T366 329T349 340T337 360Q337 365 341 369T359 385T389 414Q409 441 421 471T436 518L439 535L433 532Q427 530 415 527T389 523Q352 523 329 547'],

    // DAGGER
    0x2020: [702,211,511,64,446,'231 470Q232 471 232 473Q232 477 213 540T193 636Q192 642 192 651T204 677T239 700Q249 702 255 702Q300 702 315 660Q317 653 317 636Q317 603 298 539T279 472V470Q280 470 318 488T383 506Q408 506 423 493T442 467T446 444T443 421T424 396T383 382Q355 382 318 400T279 418Q278 416 285 392T303 334T316 284Q318 268 318 234Q318 149 311 45T296 -127T284 -203Q279 -211 255 -211Q237 -211 233 -210T226 -203Q222 -195 214 -129T199 41T192 234V245Q192 286 212 349Q233 413 231 418Q229 418 192 400T128 382Q102 382 86 396T67 421T64 444T67 466T86 492T128 506Q155 506 192 488T231 470'],

    // DOUBLE DAGGER
    0x2021: [703,202,511,64,446,'193 637Q193 663 206 679T231 698T255 702T279 699T304 679T317 637Q317 605 299 557T280 504Q280 503 281 503T320 521T382 539Q410 539 428 521T446 476Q446 454 432 434T383 414H377Q358 414 320 431T281 449L280 448Q280 444 298 396T317 316Q318 310 318 301T306 275T271 252Q261 250 255 250Q210 250 195 292Q193 299 193 316Q193 347 211 395T230 448Q230 449 229 449Q227 449 196 434Q151 414 133 414H127Q102 414 87 427T68 452T64 477Q64 503 81 521T127 539Q143 539 164 532T204 515T226 504Q230 502 230 504Q230 508 212 556T193 637ZM193 184Q193 210 206 226T231 245T255 249T279 246T304 226T317 184Q317 153 299 106T280 53Q280 51 282 51T322 68T383 86Q411 86 428 69T445 24T428 -21T382 -39Q358 -39 322 -22T282 -4Q280 -3 280 -3T280 -6Q281 -13 299 -59T317 -136Q318 -142 318 -151T306 -177T271 -200Q261 -202 255 -202Q210 -202 195 -160Q193 -153 193 -136Q193 -106 211 -60T230 -6Q230 -4 228 -4T188 -21T128 -39Q100 -39 83 -22T65 24Q65 53 82 69T127 86Q150 86 187 69T228 51Q230 50 230 50T230 53Q229 58 211 105T193 184'],

    // HORIZONTAL ELLIPSIS
    0x2026: [171,-1,1295,74,1221,'74 85Q74 121 99 146T156 171Q200 171 222 143T245 85Q245 56 224 29T160 1Q118 1 96 27T74 85ZM562 85Q562 121 587 146T644 171Q688 171 710 143T733 85Q733 56 712 29T648 1Q606 1 584 27T562 85ZM1050 85Q1050 121 1075 146T1132 171Q1176 171 1198 143T1221 85Q1221 56 1200 29T1136 1Q1094 1 1072 27T1050 85'],

    // PRIME
    0x2032: [563,-33,344,35,331,'240 563Q278 563 304 539T331 480V473Q331 462 316 431T217 236Q199 200 174 151T136 78T123 50Q113 33 105 33Q101 33 72 45T38 60Q35 63 35 65Q35 77 101 293T171 517Q182 542 202 552T240 563']
  }
);

MathJax.Ajax.loadComplete(MathJax.OutputJax.SVG.fontDir+"/Main/Bold/GeneralPunctuation.js");
