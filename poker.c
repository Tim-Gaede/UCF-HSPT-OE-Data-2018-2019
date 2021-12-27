// Elli Howard
// 2018 HSPT Online Competition
// Poker Hands

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(void){
	char *hands[] = {
		"Straight flush",
		"Four of a kind",
		"Full house",
		"Flush",
		"Straight",
		"Three of a kind",
		"Two pair",
		"Pair",
		"High card"};
	int cases;
	char line[200];
	char line2[200];
	int match;
	int match2;
	int game;
	int i;
	
	scanf("%d",&cases);
	fgets(line, 200, stdin);
	
	game = 1;
	
	while(cases-->0){
		fgets(line, 200, stdin);
		line[strcspn(line, "\n")] = '\0';
		match = 0;
		
		fgets(line2, 200, stdin);
		line2[strcspn(line2, "\n")] = '\0';
		match2 = 0;
		
		for(i = 0; i < 9; i++){
			if(strcmp(line, hands[i])==0){
				match = i;
			}
			if(strcmp(line2, hands[i])==0){
				match2 = i;
			}
		}
		
		printf("Game #%d: ", game);
		if(match < match2){
			printf("Ryan\n");
		}else{
			printf("Tyler\n");
		}
		game++;
	}
}