package com.luv2code.springboot.thymeleafdemo.service;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {
	
	public static void  merge (Integer arr[], int s, int e, int m) {

		int n1= m-s+1;
		int n2= e-m;
		
		int arr1[] = new int[n1];
		int arr2[] = new int[n2];
		
		for(int i=0; i<n1;i++) {
		
			arr1[i]= arr[s+i];
		}
		for(int i=0; i<n2;i++) {
					
			arr2[i]= arr[m+1+i];
		}
		
		int i=0, j=0, k=s;
		while(i< n1 && j<n2) {
			if(arr1[i]<arr2[j]) {
				arr[k]= arr1[i];
				i++;
			}
			else {
				arr[k]= arr2[j];
				j++;
			}
			k++;
		}
		while(i< n1) {
			arr[k]= arr1[i];
			i++;
			k++;
		}
		while(j < n2) {
			arr[k]= arr2[j];
			j++;
			k++;
		}
				
	}
	
	public static void sort (Integer arr[], int s, int e) {
		
		if(s<e) {
			int m= (s+e)/2;
			 sort(arr, s,m);
			 sort(arr, m+1, e);
			 
			 merge(arr, s,e,m);
			 
		}
		
		
	}


	public static void PrintArray(Integer arr[]) {
		
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] +" ,");
		}
		
	}

	public static void main(String[] args) {
		
		Integer arr [] = {1,2,3,4,10,5,6};
		
		System.out.println("Given array");
		PrintArray(arr);
		System.out.println();
		long startTime = System.nanoTime();
		
		Arrays.sort(arr, new Comparator<Integer>() {

			@Override
			public int compare(Integer arg0, Integer arg1) {
				if(arg0>arg1) {
					return -1;
				}
				else
				return 1;
			}
			
		});
		
		
		
		//MergeSort.sort(arr, 0, arr.length-1);
		
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
		System.out.println("Sorted Array");
		
		PrintArray(arr);
		
	}
	}


