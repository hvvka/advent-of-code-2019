input_from, input_to = 356261, 846303

combinations = [i for i in range(input_from, input_to)]
solutions = []


def has_doubles(n):
    return len(set(str(n))) < len(str(n))


def are_digits_increasing(n):
    A = [int(d) for d in str(n)]
    return all(A[i] <= A[i + 1] for i in range(len(A) - 1))


for c in combinations:
    if has_doubles(c) and are_digits_increasing(c):
        solutions.append(c)

print(len(solutions))  # 544
